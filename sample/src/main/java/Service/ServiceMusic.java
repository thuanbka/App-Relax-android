package Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import Entity.Question;
import Entity.Song;

import java.util.ArrayList;
import java.util.Random;

public class ServiceMusic extends SQLiteOpenHelper {


    public static final String DATABASE_NAME ="application";
    private static final String TABLE_NAME ="music";
    private static final String ID ="id";
    private static final String TITLE ="title";
    private static final String SINGLE ="singer";
    private static final String ICON ="icon";
    private static final String ISLIKE ="haslove";

    private static final String TABLE_NAME_QUESTION ="question";
    private static final String ASCR ="answercorect";
    private static final String QUESTION ="question";
    private static final String ASA="answerA";
    private static final String ASB ="answerB";
    private static final String ASC ="answerC";
    private static final String ASD ="answerD";
    private static final String LEVEL ="level";


    private final Context context;

    public ServiceMusic(Context context) {
        super(context, DATABASE_NAME,null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE if not exists "+TABLE_NAME +" (" +
                ID +" interger primary key ,"+
                TITLE + " TEXT,"+
                SINGLE+" TEXT,"+
                ICON +" TEXT,"+
                ISLIKE +" integer)";
        db.execSQL(sqlQuery);
        sqlQuery = "Create table if not exists word(" +
                "id integer primary key ," +
                "number integer," +
                "score integer)" ;
        db.execSQL((sqlQuery));

        sqlQuery = "CREATE TABLE if not exists " +TABLE_NAME_QUESTION +" (" +
                ID +" integer primary key autoincrement,"+
                QUESTION + " TEXT,"+
                ASA+" TEXT,"+
                ASB +" TEXT,"+
                ASC +" TEXT,"+
                ASD +" TEXT,"+
                ASCR +" interger,"+
                LEVEL +" integer)";
        db.execSQL((sqlQuery));

        db.execSQL("Insert into "+TABLE_NAME+ " values(0, 'Gặp em đúng lúc','Luân Tang','i002',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(1, 'Đúng Người Đúng Thời Điểm (Single)','Thanh Hưng','i001',1)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(2, 'Why not me','Enrique-Iglesias','i003',1)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(3, 'Con đường bình phàm','Luân Tang','i004',1)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(4, 'Người theo đuổi ánh sáng','Tử Vi','i005',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(5, 'Magic in the air','Magic System','i006',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(6, 'Way Back Home','Saun','i007',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(7, 'Save me','Deamn','i008',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(8, 'I do','911','i009',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(9, 'Thu cuối','Yanbi, Hằng BingBong, Mr.T','i010',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(10, 'Một nhà','Da LAB','i011',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(11, 'Anh khác hay em khác','Khắc Việt','i012',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(12, 'Cơn mưa ngang qua','Sơn Tùng M-TP','i013',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(13, 'Haru Haru','Big Bang','i014',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(14, 'Day By Day','T-ara','i015',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(15, 'All day long','ZE:A','i016',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(16, 'Có chàng trai viết lên cây','Phan Mạnh Quỳnh','i017',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(17, 'Tháng tư là lời nói dối của em','Hà Anh Tuấn','i018',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(18, 'Mình yêu nhau đi','Bích Phương','i019',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(19, 'Lặng Yên','Bùi Anh Tuấn - Ái Phương','i020',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(20, 'Hãy để anh yêu em lần nữa','The men','i021',0)");
        db.execSQL("Insert into "+TABLE_NAME+ " values(21, 'Thế nào là vĩnh hằng','Hồ Hạ','i022',0)");

        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Đâu là tên một loại mũ?','Lưỡi hái','Lưỡi trai','Lưỡi lê','Lưỡi rắn',1,1)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Bảy chú lùn trong chuyện cổ tích \" Nàng bạch tuyết và bảy chú lùn \" làm nghề gì?','Thợ rèn','Thợ săn','Thợ mỏ','Thợ hàn',2,2)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Người ta thường nấu canh cua với rau gì?','Rau đay','Súp lơ xanh','Củ cải','Mộc nhĩ',0,1)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Đất nước nào là quê hương của ông già tuyết?','Hà Lan','Anh','Pháp','Phần Lan',3,3)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Phim hoạt hình đầu tiên được công chiếu vào thời gian nào?','25-10-1895','01-06-1875','01-06-1893','28-10-1892',3,3)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Kinh thành trà kiệu thuộc tỉnh nào?','Quảng Bình','Quảng Nam','Huế','Bình Định',1,2)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Người đẹp vì lụa, ... tốt vì phân?','lúa','chanh','ngô','khoai',0,1)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Nóc nhà Đông Dương là đỉnh núi nào?','Đỉnh Phan-Xi-Păng','Núi Trầm','Đỉnh E-ver-ret','Đỉnh Putaleng',0,2)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Silic là hợp chất dạng gì?','Kim loại','Phi kim','Á Kim','Lỏng',2,2)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Vua nào đặt nhiều niên hiệu nhất lịch sử nước ta?','Bảo Đại','Trần Nhân Tông','Lý Thái Tông','Lý Nhân Tông',3,2)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Ở Chùa Bộc, ngoài thờ phật, nhân dân còn thờ vị tướng nào?','Quang Trung','Trần Quốc Tuấn','Trần Thủ Độ','Phạm Ngũ Lão',0,3)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Lễ hội khai ấn đền Trần diễn ra ở tỉnh nào?','Nam Định','Thái Bình','Phú Thọ','Ninh Bình',0,3)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Tiền giấy lần đầu tiên được phát hành ở Trung Quốc dưới triều đại nào?','Nhà Tống','Nhà Minh','Nhà Đường','Nhà Nguyên',2,3)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Ba thương con vì con giống ...?','Mẹ','Bà Ngoại','Bà Nội','Nữ thư kí',0,1)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Tổng ba góc trong một tam giác bằng bao nhiêu độ?','360','180','90','0',1,1)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Ai là tác giả của bộ tiểu thuyết \"Phong Vân\"?','Mã Vinh Thành','Kim Dung','La Quán Trung','Ngô Thừa Ân',0,2)");
        db.execSQL("Insert into "+TABLE_NAME_QUESTION+ " values(null, 'Đâu là một nhân vật trong tác phẩm \"Thiên Long Bát Bộ \" của tác giả Kim Dung?','Dương Quá','Mộ Dung Phục','Võ Tu Văn','Điền Bá Quang',1,2)");

        db.execSQL("Insert into word" + " values(1,1,0)");
        Log.d("tag", "CreateDatabase: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("Drop table if exists word");
        db.execSQL("Drop table if exists "+ TABLE_NAME_QUESTION);
        onCreate(db);
    }

    //Cập nhật dữ liệu cho bảng word thành công trả về gái trị khác -1 thất bại trả về -1
    public int UpdateWord( int vt, int diem)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("number", vt);
        values.put("score", diem);
        int re = db.update("word",values,"id = ?",new String[] { String.valueOf(1)});
        db.close();
        return re;

    }

    // lấy câu hỏi và số điểm hiện tại
    public int [] getWord()
    {
        int []a =new int[2];
        String selectQuery = "SELECT  * FROM word";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                a[0]=cursor.getInt(1);
                a[1]=cursor.getInt(2);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return a;
    }

//    public void addSong(Song song){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ID,new Random().nextInt(100));
//        values.put(TITLE, song.getTitle());
//        values.put(SINGLE, song.getSinger());
//        values.put(ICON, song.getIcon());
//        if(!song.isLike())
//        {
//            values.put(ISLIKE, 0);
//        }
//        else
//        {
//            values.put(ISLIKE, 1);
//        }
//
//        //Neu de null thi khi value bang null thi loi
//
//        db.insert(TABLE_NAME,null,values);
//        //Toast.makeText(context, "Add successfylly" +Song.getSong()+"\n"+values.size(), Toast.LENGTH_SHORT).show();
//        db.close();
//    }

    /*
    Select a  by ID
     */

//    public  Song getSongById(int id){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE "+ID + " = " +id;
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        Log.d("playmusic1234","looix");
//        Song song = new Song();
//        if (cursor != null && cursor.moveToFirst()) {
//                song.setId(cursor.getInt(0));
//                song.setTitle(cursor.getString(1));
//                song.setSinger(cursor.getString(2));
//                song.setIcon(cursor.getString(3));
//                if(cursor.getInt(4)==1)
//                {
//                    song.setLike(true);
//                }
//                else
//                {
//                    song.setLike(false);
//                }
//        }
//        cursor.close();
//        db.close();
//        return song ;
//    }

    //Cập nhật trạng thái yêu thích của bài hát
    public int Update( Song song){
        SQLiteDatabase db = this.getWritableDatabase();
        int love;
        if(!song.isLike())
        {
            love=0;
        }
        else
        {
            love =1;
        }
        int res =-1;
        try {
            String updatequery= "UPDATE "+ TABLE_NAME + " SET "+ ISLIKE + " = "+love + " WHERE "+ ID + " = " +song.getId();
            db.execSQL(updatequery);
            res=0;
        }
        catch(Exception ex)
        {
            res=-1;
        }
        db.close();
        return res;
    }

    /*Lấy danh sách tất cả các bài hát*/
    public ArrayList<Song> getAllSong() {
        ArrayList<Song> listSong = new ArrayList<Song>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(cursor.getInt(0));
                song.setTitle(cursor.getString(1));
                song.setSinger(cursor.getString(2));
                song.setIcon(cursor.getString(3));
                if(cursor.getInt(4)==1)
                {
                    song.setLike(true);
                }
                else
                {
                    song.setLike(false);
                }
                listSong.add(song);
            } while (cursor.moveToNext());
        }
        else
        {
            Toast.makeText(context, "Không có bài hát trong danh sách", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return listSong;
    }

    // Lấy danh sách bài hát yêu thích
    public ArrayList<Song> getListLoveSong() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Song> listSong = new ArrayList<Song>();
        // Select All Query
        String selectQuery = "Select * from "+TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                if(cursor.getInt(4)==1) {
                    Song song = new Song();
                    song.setId(cursor.getInt(0));
                    song.setTitle(cursor.getString(1));
                    song.setSinger(cursor.getString(2));
                    song.setIcon(cursor.getString(3));
                    song.setLike(true);
                    listSong.add(song);
                }
            } while (cursor.moveToNext());
        }
        else
        {
            Toast.makeText(context, "Không có bài hát nào được yêu thích", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return listSong;
    }

    // Lấy danh sách tất cả các câu hỏi
    public ArrayList<Question> getAllQuestion() {
        ArrayList<Question> listQuestion = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_QUESTION +" where id > 15";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setAnswerA(cursor.getString(2));
                question.setAnswerB(cursor.getString(3));
                question.setAnswerC(cursor.getString(4));
                question.setAnswerD(cursor.getString(5));
                question.setDapan(cursor.getInt(6));
                question.setLevel(cursor.getInt(7));
                listQuestion.add(question);
            } while (cursor.moveToNext());
        }
        else
        {
            Toast.makeText(context, "Không có câu hỏi trong danh sách", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return listQuestion;
    }

    //Thêm câu hỏi
    public void addQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUESTION,question.getQuestion());
        values.put(ASA, question.getAnswerA());
        values.put(ASB, question.getAnswerB());
        values.put(ASC, question.getAnswerC());
        values.put(ASD, question.getAnswerD());
        values.put(ASCR, question.getDapan());
        values.put(LEVEL,question.getLevel());
        db.insert(TABLE_NAME_QUESTION,null,values);
        db.close();
    }

    //Xóa câu hỏi
    public void deleteQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_QUESTION, ID + " = ?",
                new String[] { String.valueOf(question.getId()) });
        db.close();
    }

    // Cập nhật câu hỏi
    public int upDateQuestion(Question question)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUESTION,question.getQuestion());
        values.put(ASA, question.getAnswerA());
        values.put(ASB, question.getAnswerB());
        values.put(ASC, question.getAnswerC());
        values.put(ASD, question.getAnswerD());
        values.put(ASCR, question.getDapan());
        values.put(LEVEL,question.getLevel());
        int res = db.update(TABLE_NAME_QUESTION,values,"id = ?",new String[] { String.valueOf(question.getId())});
        db.close();
        return res;
    }

    //Lấy câu hỏi để chơi
    public ArrayList<Question> getData(){
        ArrayList<Question> arrQuestions=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //lấy 5 câu hỏi dễ
        String sql="SELECT * FROM "+TABLE_NAME_QUESTION+ " where level = 1 ORDER BY random() limit 5";
        Cursor cursor= db.rawQuery(sql,null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setAnswerA(cursor.getString(2));
                question.setAnswerB(cursor.getString(3));
                question.setAnswerC(cursor.getString(4));
                question.setAnswerD(cursor.getString(5));
                question.setDapan(cursor.getInt(6));
                question.setLevel(cursor.getInt(7));
                arrQuestions.add(question);
            } while (cursor.moveToNext());
        }

        //lấy 5 câu hỏi trung bình
       sql="SELECT * FROM "+TABLE_NAME_QUESTION+ " where level = 2 ORDER BY random() limit 5";
        cursor= db.rawQuery(sql,null);
       if( cursor != null && cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setAnswerA(cursor.getString(2));
                question.setAnswerB(cursor.getString(3));
                question.setAnswerC(cursor.getString(4));
                question.setAnswerD(cursor.getString(5));
                question.setDapan(cursor.getInt(6));
                question.setLevel(cursor.getInt(7));
                arrQuestions.add(question);
            } while (cursor.moveToNext());
       }

       //lấy 5 câu hỏi khó
       sql="SELECT * FROM "+TABLE_NAME_QUESTION+ " where level = 3 ORDER BY random() limit 5";
       cursor= db.rawQuery(sql,null);
       if (cursor != null && cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setAnswerA(cursor.getString(2));
                question.setAnswerB(cursor.getString(3));
                question.setAnswerC(cursor.getString(4));
                question.setAnswerD(cursor.getString(5));
                question.setDapan(cursor.getInt(6));
                question.setLevel(cursor.getInt(7));
                arrQuestions.add(question);
            } while (cursor.moveToNext());
       }

       cursor.close();
        db.close();
        return arrQuestions;
    }
}
