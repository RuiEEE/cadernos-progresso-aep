package pt.ruie.cadernoprogresso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	private static String DB_PATH = "/data/data/pt.ruie.cadernoprogresso/databases/";
	private static String DB_NAME = "provas";
	private SQLiteDatabase myDatabase;
	private Context context;
	
	public static final String TABLE_ESPECIALIDADES = "especialidades";
	
	Calendar c;
	
	public static final int DB_VERSION = 4;
	
	public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public void createDatabase() throws IOException {
        boolean vtVarMi = isDatabaseExist();

        if (!vtVarMi) {
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private boolean isDatabaseExist() {
        SQLiteDatabase kontrol = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            kontrol = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            kontrol = null;
        }

        if (kontrol != null) {
            kontrol.close();
        }
        return kontrol != null;
    }

    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);        
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}
	
	public void forceUpdate(){
		context.deleteDatabase(DB_PATH+DB_NAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("DBHelper","Upgradin..");
		
	}
	

}
