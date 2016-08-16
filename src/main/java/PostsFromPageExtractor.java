import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import facebook4j.json.DataObjectFactory;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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

        // For all xx feeds...
        for (int i = 0; i < feeds.size(); i++) {
            // Get post.
            Post post = feeds.get(i);
            // Get (string) message.
            String message = post.getMessage();
            // Print out the message.

            PagableList<Comment> comments = post.getComments();
            String date = post.getCreatedTime().toString();
            String name = post.getFrom().getName();
            String id = post.getId();

            System.out.println(post);
            postWriter.println(post);

            for (int j = 0; j < comments.size(); j++) {
                // Get post.
                Comment comment = comments.get(j);
                // Get (string) message.
                String messageC = comment.getMessage();
                // Print out the message.
                commentWriter.println(comment);
                System.out.println("COMMENT #" + j + " : " + messageC);
            }

        }
            postWriter.close();
            commentWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}