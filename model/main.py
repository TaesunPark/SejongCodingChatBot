from flask import Flask
from flask import request
from chatbot.config.DatabaseConfig import *
from chatbot.utils.Database import Database
from chatbot.utils.Preprocess import Preprocess
from chatbot.models.intent.IntentModel import IntentModel
from chatbot.models.ner.NerModel import NerModel
from chatbot.utils.GiveAnswer import GiveAnswer
from recommend.recommend import Recommendation
import os
from konlpy.tag import Komoran
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from re import match
from chatbot.utils.Log import logger

app = Flask(__name__)

#loca = os.getcwd()
loca = '.'
############# chatbot #############

# 전처리 객체 생성
p = Preprocess(word2index_dic= loca + '/chatbot/train_tools/dict/chatbot_dict.bin',
               userdic= loca +'/chatbot/utils/user_dic.tsv')

# 질문/답변 학습 DB 연결 객체 생성
db = Database(
    host=DB_HOST, user=DB_USER, password=DB_PASSWORD, db_name=DB_NAME
)
db.connect()  # DB 연결

intent = IntentModel(model_name= loca +'/chatbot/models/intent/intent_model.h5', preprocess=p)
ner = NerModel(model_name= loca +'/chatbot/models/ner/ner_model.h5', preprocess=p)

question = GiveAnswer(db=db)

############# recommend #############

reco = Recommendation()
komoran = Komoran(userdic=loca +'/recommend/user_dic.txt')
recoCPreProcessing = reco.preProcessC(db=db)
recoPythonPreProcessing = reco.preProcessPython(db=db)

@app.route('/predict/bot_response', methods=['POST'])
def predictBotResonse():
    req = request.get_json()
    msg = req.get("message")
    language = req.get("botLang")
    logger.debug(msg + "   lang : " + language)
    print(msg + "   lang : " + language)

    c_data = reco.insertUserData(recoCPreProcessing, komoran, msg)
    python_data = reco.insertUserData(recoPythonPreProcessing, komoran, msg)

    if language == 'c':
        result_reco = reco.get_recommendations(c_data, len(c_data) - 1)
        c_data = reco.deleteUserData(c_data)
    else:
        result_reco = reco.get_recommendations(python_data, len(python_data) - 1)
        python_data = reco.deleteUserData(python_data)

    logger.debug(result_reco)
    print(result_reco)

    result_reco_title = []
    for i in range(len(result_reco)):
        if(result_reco[i].get('intent') != '인사'):
            result_reco_title.append(result_reco[i].get('title'))

    result_chatbot = question.give_answer(msg, intent, ner, language, result_reco)

    result_dict = dict()
    result_dict["botMsg"] = result_chatbot
    result_dict["reco"] = result_reco_title
    logger.debug(result_dict)
    print(result_dict)
    return result_dict

if __name__ == '__main__':
    #app.run(host='0.0.0.0', port=8082)
    app.run(debug=True)


