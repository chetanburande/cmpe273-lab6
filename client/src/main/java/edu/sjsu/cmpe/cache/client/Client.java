package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        char[] value = {'a','b','c','d','e','f','g','h','i','j'};
        ArrayList<DistributedCacheService> serverList = new ArrayList<DistributedCacheService>();
        
        serverList.add(new DistributedCacheService("http://localhost:3000"));
        serverList.add(new DistributedCacheService("http://localhost:3001"));
        serverList.add(new DistributedCacheService("http://localhost:3002"));
        
        //CacheServiceInterface cache = new DistributedCacheService("http://localhost:3000");
        //PUT 
        for(int i=1;i<=10;i++){
        int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), serverList.size());
        serverList.get(bucket).put(i, Character.toString(value[i-1]));
        System.out.println("Key :" + i +" and Value : "+ value[i-1] + " - routed to server - localhost:300" + bucket);
        
        }
        
        //GET
        for(int i=1; i<=10; i++)	{
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), serverList.size());
        	System.out.println("Key : " + i + " and Value :"+ value[i-1]+ " - fetched from cache server - localhost:300" +bucket);      			
        }
        
        
        /*cache.put(1, "foo");
        System.out.println("put(1 => foo)");

        String value = cache.get(1);
        System.out.println("get(1) => " + value);*/

        System.out.println("Exiting Cache Client...");
    }

}
