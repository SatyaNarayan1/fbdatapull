import facebook4j.*;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.json.DataObjectFactory;


import java.io.*;

/**
 * Created by psatya on 16/08/16.
 */
public class SearchFB {


    public static void main (String[] args) {
      //  ConfigurationBuilder cb = new ConfigurationBuilder();
       // cb.setJSONStoreEnabled()
//        cb.setDebugEnabled(true)
//                .setOAuthAppId("144749428931084")
//                .setOAuthAppSecret("592860f4aea8b39193412f48cefe8022")
//                .setOAuthAccessToken("144749428931084|2xRk5xfqh0zWDfSF8DOdVGwA0TU")
//                .setOAuthPermissions("email,public_profile,user_friends");
//        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = new FacebookFactory().getInstance();


        // facebook.getOAuthAppAccessToken();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {



            br = new BufferedReader(new FileReader(args[0]));
            PrintWriter userWriter = new PrintWriter("user_details.json", "UTF-8");

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] user = line.split(cvsSplitBy);

                System.out.println("fetching detail for user " + user[1]+ "]");




           ResponseList<User> results = facebook.searchUsers(user[1].replaceFirst("MR ",""),new Reading().limit(2));
           // ResponseList<Post> results = facebook.searchPosts("ICICILombard");
            for(User u : results)
            { String json  = DataObjectFactory.getRawJSON(u);
              //  System.out.println( json);
                userWriter.println(json);
              //  userWriter.println(u);
            }

            }
        userWriter.close();
        }catch( FacebookException fe )
        {
            System.out.println(fe.getErrorMessage());
        } catch (FileNotFoundException e) {
        e.printStackTrace();
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }


}
