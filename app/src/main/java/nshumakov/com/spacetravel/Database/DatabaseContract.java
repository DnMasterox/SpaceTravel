package nshumakov.com.spacetravel.Database;

import android.provider.BaseColumns;

/**
 * Created by nshumakov on 06.07.2017.
 */

public class DatabaseContract {

    /**
     * Describes History Table and model.
     */
    public static class Stats {

        /**
         * Default "ORDER BY" clause.
         */
        //сортируем по имени в убывающем порядке
        public static final String DEFAULT_SORT = ScoresColumns.NAME + " DESC";
        //имя таблицы
        public static final String TABLE_NAME = "Stats";
        //поле имя
        private String name;
        //наш айдишник
        private long id;
        //и сколько лет
        private int score;

        //
        // Ниже идут сетеры и гетеры для захвата данных из базы
        //
        public String getName() {

            return name;
        }

        public long getId() {

            return id;
        }
        public void setName(String name) {

            this.name = name;
        }

        public void setId(long id) {

            this.id = id;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        //Класс с именами наших полей в базе
        public class ScoresColumns implements BaseColumns {

            /**
             * Strings
             */
            public static final String NAME = "name";

            /**
             * String
             */
            public static final String SCORE = "score";
        }
    }
}