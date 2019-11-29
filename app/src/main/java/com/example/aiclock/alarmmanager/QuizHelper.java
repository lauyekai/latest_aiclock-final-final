package com.example.aiclock.alarmmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizHelper extends SQLiteOpenHelper {
    Context context;
    private static final int DATABASE_VERSION = 13;
 static Random rand = new Random();

    static int random = rand.nextInt((100 - 1) + 1) +1;
    private static final String DATABASE_NAME = "mathsone"+random;
    private static final String TABLE_QUEST = "quest";
    private static final String KEY_ID = "qid";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_OPTA = "opta";
    private static final String KEY_OPTB = "optb";
    private static final String KEY_OPTC = "optc";

    private SQLiteDatabase dbase;

    public QuizHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER + " TEXT, " + KEY_OPTA + " TEXT, "
                + KEY_OPTB + " TEXT, " + KEY_OPTC + " TEXT)";
        db.execSQL(sql);
        addQuestion();
        // db.close();
    }

    private void addQuestion() {
        Random rand = new Random();
        int answer = 0;
        String quetionShow="";
        Question questionArray;

        for (int i=0;i<=20;i++)
        {
            int firstNum = rand.nextInt((20 - 1) + 1) +1;
            int SecondNum = rand.nextInt((20 - 1) + 1) +1;

            int plusorminus =rand.nextInt((2 - 1) + 1) +1;
            if(plusorminus==1)
            {
                answer= firstNum + SecondNum;
                quetionShow= firstNum+"+"+SecondNum+"=?";
            }
            if(plusorminus==2)
            {
                answer= firstNum - SecondNum;
                quetionShow= firstNum+"-"+SecondNum+"=?";
            }



            int a1= answer+1;
            int a2= answer-1;




            questionArray= new Question(quetionShow, Integer.toString(a1), Integer.toString(answer), Integer.toString(a2), Integer.toString(answer));
            this.addQuestion(questionArray);
        }


// nextInt as provided by Random is exclusive of the top value so you need to add 1


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);

        onCreate(db);
    }


    public void addQuestion(Question quest) {


        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());


        dbase.insert(TABLE_QUEST, null, values);
    }

    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();

        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Question quest = new Question();
            quest.setID(cursor.getInt(0));
            quest.setQUESTION(cursor.getString(1));
            quest.setANSWER(cursor.getString(2));
            quest.setOPTA(cursor.getString(3));
            quest.setOPTB(cursor.getString(4));
            quest.setOPTC(cursor.getString(5));

            quesList.add(quest);
        }

        return quesList;
    }


}
