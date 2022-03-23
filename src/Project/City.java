package Project;

public class City {
    public String name;
    public String cityId;
    public String connectionUrl;

    public City(String newName, String newCityId, String newConnectionUrl) {
        this.name = newName;
        this.cityId = newCityId;
        this.connectionUrl = newConnectionUrl;
    }
}
class CityArray {
    City city1 = new City("city1","001","jdbc:mysql://city1_host:3306/db_name");
    City city2 = new City("city2","002","jdbc:oracle:thin:@city2:1521:db_name");
    City city3 = new City("city3","003","jdbc:postgresql://city3_host:5432/db_name");

    City [] cityArray = {city1, city2, city3}

    public City[] GetCityArray(){
        return cityArray;
    }
}

