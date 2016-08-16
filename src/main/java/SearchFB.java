import facebook4j.*;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.json.DataObjectFactory;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by psatya on 16/08/16.
 */
public class SearchFB {


    public static void main (String[] args) {
       // ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(true)
//                .setOAuthAppId("144749428931084")
//                .setOAuthAppSecret("592860f4aea8b39193412f48cefe8022")
//                .setOAuthAccessToken("144749428931084|2xRk5xfqh0zWDfSF8DOdVGwA0TU")
//                .setOAuthPermissions("email,public_profile,user_friends");
//        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = new FacebookFactory().getInstance();

        // facebook.getOAuthAppAccessToken();
        try {
            PrintWriter postWriter = new PrintWriter("user_details.json", "UTF-8");
            //facebook.postStatusMessage("Hello World from Facebook4J.");

           ResponseList<User> results = facebook.searchUsers(args[0],new Reading().limit(5));
           // ResponseList<Post> results = facebook.searchPosts("ICICILombard");
            for(User u : results)
           System.out.println( u);
        }catch( FacebookException fe )
        {
            System.out.println(fe.getErrorMessage());
        } catch (FileNotFoundException e) {
        e.printStackTrace();
          } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    }


}
