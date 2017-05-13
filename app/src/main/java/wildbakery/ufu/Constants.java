package wildbakery.ufu;

/**
 * Created by DIKII PEKAR on 01.12.2016.
 */

public class Constants {
    public static class HTTP {
        public static  final  String BASE_URL = "http://vuz.dev.webant.ru/api/";
        public  static final  String IMAGE_URL = "http://vuz1.webant.ru/uploads/";
    }
    public static  class  MONTHS{
        public static String [] month = {"","Января","Февраля","Марта","Апреля","Мая","Июня","Июля","Августа","Сентября","Октября","Ноября","Декабря"};
        public static String [] month1 = {"","Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    }

    public static class TABLES{

        public static final String TABLE_NEWS = "TABLE_NEWS";
        public static final String TABLE_IMAGES = "TABLE_IMAGES";
        public static final String TABLE_JOBS = "TABLE_JOBS";
        public static final String TABLE_SALES = "TABLE_SALES";
        public static final String TABLE_EVENT = "TABLE_JOBS";

        public static final String COLUMN_PK_ID = "PK_ID";
    }

}
