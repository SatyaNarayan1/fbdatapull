import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.json.DataObjectFactory;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class PostsFromPageExtractor {

    /**
     * A simple Facebook4J client which
     * illustrates how to access group feeds / posts / comments.
     *
     * @param args
     * @throws FacebookException
     */
    public static void main(String[] args) throws FacebookException {

        // Generate facebook instance.
        Facebook facebook = new FacebookFactory().getInstance();
      //  System.out.println(facebook.getConfiguration().isJSONStoreEnabled() );

        // Use default values for oauth app id.
//        facebook.setOAuthAppId("296251414082104","cb99043a762d7fbb1f16c6f19284c9fa");
//        // Get an access token from:
//        // https://developers.facebook.com/tools/explorer
//        // Copy and paste it below.
//        String accessTokenString = "EAACEdEose0cBAFmYg6yPbngHfhQw8XdNXS3qMDiDQPPvvVXnQdpARCm3ZAbnF09noOWcVCQ0PfhYt39Qz8PGZBRGoA5DaTOaId99msz5z8t5D0b5Vvpu1moZAouOnsBA4co7drOyhzotLLdiJMizZCfGlOinFxZCFRZBWGJmDq2AZDZD";
//        AccessToken at = new AccessToken(accessTokenString);
//        // Set access token.
//        facebook.setOAuthAccessToken(at);

        // We're done.
        // Access group feeds.
        // You can get the group ID from:
        // https://developers.facebook.com/tools/explorer

        // Set limit to 25 feeds.
        String feedId="";
        if(args.length<1)
        {
            feedId="192320847482725";
        }
        else
        {
            feedId=args[0];
        }
        ResponseList<Post> feeds = facebook.getFeed(feedId);
//ResponseList<Post> feeds = facebook.getFeed("700329662",
        //          new Reading().limit(25));
        try {
        PrintWriter postWriter = new PrintWriter(feedId+"_post.json", "UTF-8");
        PrintWriter commentWriter = new PrintWriter(feedId+"_comment.json", "UTF-8");
            PrintWriter likesWriter = new PrintWriter(feedId+"_likes.json", "UTF-8");

        // For all xx feeds...
        for (int i = 0; i < feeds.size(); i++) {
            // Get post.
            Post post = feeds.get(i);
            // Get (string) message.
            String message = post.getMessage();
            // Print out the message.

            PagableList<Comment> comments = post.getComments();
            PagableList<Like> likes = post.getLikes();
          //  String date = post.getCreatedTime().toString();
           // String name = post.getFrom().getName();
            String id = post.getId();
            String json  = DataObjectFactory.getRawJSON(post);
           // System.out.println(json);
            postWriter.println(json);
            System.out.println(comments);
            System.out.println(likes);
            String parentId = id;
            for (int j = 0; j < comments.size(); j++) {
                // Get post.
                Comment comment = comments.get(j);

                String msg = comment.getMessage();
                String commentId = comment.getId();
                JSONObject obj = new JSONObject();
                obj.put("parentId", parentId);
                obj.put("id", commentId);
                obj.put("message",msg);
                // Print out the message.
               // String commentJson  = DataObjectFactory.getRawJSON(comment);

                commentWriter.println(obj.toString());

            }
            for (int j = 0; j < likes.size(); j++) {
                // Get post.
                Like like = likes.get(j);
                // Get (string) message.
                String likeId = like.getId();
                String category = like.getCategory();
                String name = like.getName();
              //  String lik eJson  = DataObjectFactory.getRawJSON(like);

                JSONObject obj = new JSONObject();
                obj.put("parentId", parentId);
                obj.put("id", likeId);
                obj.put("name",name);
                obj.put("category",category);
                likesWriter.println(obj.toString());

            }


        }
            postWriter.close();
             commentWriter.close();
            likesWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}