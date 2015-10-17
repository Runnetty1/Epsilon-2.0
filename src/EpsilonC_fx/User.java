/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EpsilonC_fx;

/**
 *
 * @author Runnetty
 */
public class User {
    private String name,password;
    
    User(){}
    User(String name,String password){
        this.name = name;
        this.password = password;
    }
    
    public String getName(){return this.name;}
    public String getPassword(){return this.password;}
}
