package com.epam.lab.pojo;

public class Author {

    private long authorId;

    private Name authorName;
    public static class Name {

        private String first;
        private String second;

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }

        public Name(String firstName, String secondName) {
            this.first = firstName;
            this.second = secondName;
            }

        @Override
        public String toString() {
            return "Name{" +
                    "first='" + first + '\'' +
                    ", second='" + second + '\'' +
                    '}';
        }
    }

    private String nationality;

    private Birth birth;
    public static class Birth {

        private String date;
        private String country;
        private String city;

        public Birth(String date, String country, String city) {
            this.date = date;
            this.country = country;
            this.city = city;
        }

        public String getDate() {
            return date;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "Birth{" +
                    "date='" + date + '\'' +
                    ", country='" + country + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }

    private String authorDescription;

    public Author(long authorId, Name authorName, String nationality, Birth birth, String authorDescription) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.nationality = nationality;
        this.birth = birth;
        this.authorDescription = authorDescription;
    }

    public long getAuthorId() {
        return authorId;
    }

    public Name getAuthorName() {
        return authorName;
    }

    public String getNationality() {
        return nationality;
    }

    public Birth getBirth() {
        return birth;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", authorName=" + authorName +
                ", nationality='" + nationality + '\'' +
                ", birth=" + birth +
                ", authorDescription='" + authorDescription + '\'' +
                '}';
    }
}
