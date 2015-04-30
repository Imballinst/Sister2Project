package sister2project;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Paulus
 */
public final class Client {
    private static JSONObject req; // JSON request
    private static JSONObject res; // JSON response
    
    // Variabel untuk Client
    private static boolean gameMenu = false;
    private static boolean tradeMenu = false;
    private static int findItem = -99;
    private static boolean findMenu = false;
    
    private static boolean hasLogin = false;
    private static boolean online = false;
    
    private static String token; // token dari server
    private static int x, y; // posisi
    private static String name; // nama client
    private static JSONArray inventory; // inventory
    private static Calendar timeLogin = new GregorianCalendar(); // time
    private static Calendar timeTravel = new GregorianCalendar(); // time
    
    // Variabel untuk game
    private static int numTrades = 0;
    private static int numFinds = 0;
    private static String listOffer[][]; // list offer
    private static String listStatusOffer[]; // list status offer
    private static String listTokenOffer[]; // list token offer
    private static String listFound[][]; // list offer
    private static String listStatusFound[]; // list status offer
    private static String listTokenFound[]; // list token offer
    private static int width, height; // dimensi map

    //----- Getter & Setter -----//
    public static void incrementTime() {
        timeLogin.add(Calendar.MILLISECOND, 200);
    }
    
    public static String getListFind(int i, int j) {
        return listFound[i][j];
    }
    
    public static String getListStatusFind(int i) {
        return listStatusFound[i];
    }
    
    public static String getListTokenFind(int i) {
        return listTokenFound[i];
    }
    
    public static String getListOffer(int i, int j) {
        return listOffer[i][j];
    }
    
    public static String getListStatus(int i) {
        return listStatusOffer[i];
    }
    
    public static String getListToken(int i) {
        return listTokenOffer[i];
    }
    
    public static int getFindItem() {
        return findItem;
    }
    
    public static void setFindItem(int _findItem) {
        findItem = _findItem;
    }
    
    public static boolean getFindMenu() {
        return findMenu;
    }
    
    public static void setFindMenu(boolean _findMenu) {
        findMenu = _findMenu;
    }
    
    public static boolean getTradeMenu() {
        return tradeMenu;
    }
    
    public static void setTradeMenu(boolean _tradeMenu) {
        tradeMenu = _tradeMenu;
    }
    
    public static boolean getOnline() {
        return online;
    }
    
    public static void setOnline(boolean _online) {
        online = _online;
    }
    
    public static int getNumFinds() {
        return numFinds;
    }
    
    public static int getNumTrades() {
        return numTrades;
    }
    
    public static void setGameScene(boolean b) {
        gameMenu = b;
    }
    
    public static boolean getGameScene() {
        return gameMenu;
    }
    
    public static void setLogin(boolean b) {
        hasLogin = b;
    }
    
    public static boolean getLogin() {
        return hasLogin;
    }
    
    public static JSONArray getInventory() {
        return inventory;
    }
    
    public static JSONObject getReq() {
        return req;
    }
    
    public static JSONObject getRes() {
        return res;
    }
    
    public static void setRes(String response) throws JSONException {
        res = new JSONObject(response);
    }
    
    public static String getToken() {
        return token;
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public static String getName() {
        return name;
    }

    public static Calendar getTimeTravel() {
        return timeTravel;
    }
    
    public static Calendar getTimeLogin() {
        return timeLogin;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;                                           
    }

    public static void setToken(String _token) {
        token = _token;
    }

    public static void setX(int _x) {
        x = _x;
    }

    public static void setY(int _y) {
        y = _y;
    }

    public static void setName(String _name) {
        name = _name;
    }

    public static void setWidth(int _width) {
        width = _width;
    }

    public static void setHeight(int _height) {
        height = _height;
    }
    
    
    //----- Method untuk JSON kirim -----//
    public static void SignUp(String username, String password){
        try{
            // Jangan lupa pass nya dikirim dengan enkripsi
            req = new JSONObject();
            req.put("method","signup");
            req.put("username",username);
            req.put("password",password);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void Login(String username, String password){
        try{
            // Jangan lupa pass nya dikirim dengan enkripsi
            req = new JSONObject();
            req.put("method","login");
            req.put("username",username);
            req.put("password",password);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void Inventory(String token){
        try{
            req = new JSONObject();
            req.put("method","inventory");
            req.put("token",token);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void MixItem(String token, int idItem1, int idItem2){
        try{
            // idItem masih pake int, kalo mau jadi String silahkan
            req = new JSONObject();
            req.put("method","mixitem");
            req.put("token",token);
            req.put("item1",idItem1);
            req.put("item2",idItem2);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void Map(String token){
        try{
            req = new JSONObject();
            req.put("method","map");
            req.put("token",token);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void Move(String token, int x, int y){
        try{
            req = new JSONObject();
            req.put("method","move");
            req.put("token",token);
            req.put("x",x);
            req.put("y",y);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void Field(String token){
        try{
            req = new JSONObject();
            req.put("method","field");
            req.put("token",token);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void Offer(String token, int idItemOffered, int n1, int idItemDemanded, int n2){
        try{
            req = new JSONObject();
            req.put("method","offer");
            req.put("token",token);
            req.put("offered_item",idItemOffered);
            req.put("n1",n1);
            req.put("demanded_item",idItemDemanded);
            req.put("n2",n2);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void TradeBox(String token){
        try{
            req = new JSONObject();
            req.put("method","tradebox");
            req.put("token",token);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void SendFind(String token, int idItem){
        try{
            req = new JSONObject();
            req.put("method","sendfind");
            req.put("token",token);
            req.put("item",idItem);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void SendAccept(String token, String offertoken){
        try{
            req = new JSONObject();
            req.put("method","sendaccept");
            req.put("token",token);
            req.put("offer_token",offertoken);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void FetchItem(String token, String offertoken){
        try{
            req = new JSONObject();
            req.put("method","fetchitem");
            req.put("token",token);
            req.put("offer_token",offertoken);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void CancelOffer(String token, String offertoken){
        try{
            req = new JSONObject();
            req.put("method","canceloffer");
            req.put("token",token);
            req.put("offer_token",offertoken);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //----- Method untuk masukin ke JSON res -----//
    public static void responseToJSON(String response){
        try{
            res = new JSONObject(response);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //----- Method untuk JSON terima -----//
    public static void resSignUp(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                // Do nothing
                System.out.println("ok");
                break;
                    
                case "fail":
                String description = res.getString("description");
                System.out.println("fail : " + description);
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resLogin(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                // token, x, dan y langsung dimasukkan ke dalam variabel global
                token = res.getString("token");
                x = res.getInt("x");
                y = res.getInt("y");
                timeLogin.setTimeInMillis(res.getLong("time") * 1000);
                System.out.println("ok");
                break;
                
                case "fail":
                String description = res.getString("description");
                System.out.println("fail : " + description);
                break;
                    
                case "error":
                // Do nothing
                break;    
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resInventory(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                inventory = (JSONArray) res.get("inventory");
                System.out.println("ok");
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;    
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resMixItem(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                int idItemMixed = res.getInt("item");
                System.out.println("ok");
                break;    
                    
                case "fail":
                String description = res.getString("description");
                System.out.println("fail : " + description);
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resMap(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                // Nama, width, dan height dari map dimasukkan ke dalam variabel global
                name = res.getString("name");
                width = res.getInt("width");
                height = res.getInt("height");
                System.out.println("ok");
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resMove(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                timeTravel.setTimeInMillis(res.getLong("time") * 1000);
                x = req.getInt("x");
                y = req.getInt("y");
                System.out.println("ok");
                break;
                    
                case "fail":
                String description = res.getString("description");
                System.out.println("fail : " + description);
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resField(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                int idItem = res.getInt("item");
                System.out.println("ok");
                break;
                    
                case "fail":
                String description = res.getString("description");
                System.out.println("fail : " + description);
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resOffer(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                // Do nothing
                System.out.println("ok");
                break;
                    
                case "fail":
                String description = res.getString("description");
                System.out.println("fail : " + description);
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resTradeBox(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                JSONArray listTrade = (JSONArray) res.get("offers");
                numTrades = listTrade.length();
                listOffer = new String[numTrades][4];
                listStatusOffer = new String[numTrades];
                listTokenOffer = new String[numTrades];
                for (int i=0;i<listTrade.length();i++){
                    for (int j=0;j<6;j++){
                        if(j==4){ // untuk status
                            listStatusOffer[i] =  String.valueOf(((JSONArray)listTrade.get(i)).getBoolean(j));
                        }
                        else if(j==5){ // untuk token
                            listTokenOffer[i] = String.valueOf(((JSONArray)listTrade.get(i)).getString(j));
                        }
                        else{ // untuk list offer
                            listOffer[i][j] = String.valueOf(((JSONArray)listTrade.get(i)).getInt(j));
                        }
                    }
                }
                System.out.println("ok");
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resSendFind(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                JSONArray listTradeFind = (JSONArray) res.get("offers");
                numFinds = listTradeFind.length();
                listFound = new String[numFinds][4];
                listStatusFound = new String[numFinds];
                listTokenFound = new String[numFinds];
                for (int i=0;i<listTradeFind.length();i++){
                    for (int j=0;j<6;j++){
                        if(j==4){ // untuk status
                            listStatusFound[i] =  String.valueOf(((JSONArray)listTradeFind.get(i)).getBoolean(j));
                        }
                        else if(j==5){ // untuk token
                            listTokenFound[i] = String.valueOf(((JSONArray)listTradeFind.get(i)).getString(j));
                        }
                        else{ // untuk list offer
                            listFound[i][j] = String.valueOf(((JSONArray)listTradeFind.get(i)).getInt(j)); 
                        }
                    }
                }
                System.out.println("ok");
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resSendAccept(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                // Do nothing
                System.out.println("ok");
                break;
                    
                case "fail":
                String description = res.getString("description");
                System.out.println("fail : " + description);
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resFetchItem(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                // Do nothing
                System.out.println("ok");
                break;
                    
                case "fail":
                String description = res.getString("description");
                System.out.println("fail : " + description);
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void resCancelOffer(){
        try{
            String status = res.getString("status");
            switch(status){
                case "ok":
                // Do nothing
                System.out.println("ok");
                break;
                    
                case "fail":
                String description = res.getString("description");
                System.out.println("fail : " + description);
                break;
                    
                case "error":
                // Do nothing
                System.out.println("error");
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
