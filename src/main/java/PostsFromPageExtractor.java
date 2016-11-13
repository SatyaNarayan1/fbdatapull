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
     *
     * @param args
     * @throws FacebookException
     */
    public static void main(String[] args) throws FacebookException {

        // Generate facebook instance.
        Facebook facebook = new FacebookFactory().getInstance();

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

        int currentCount = 0;
        try {
            PrintWriter postWriter = new PrintWriter(feedId + "_post.json", "UTF-8");
            PrintWriter commentWriter = new PrintWriter(feedId + "_comment.json", "UTF-8");
            PrintWriter likesWriter = new PrintWriter(feedId + "_likes.json", "UTF-8");


        while(true){

            ResponseList<Post> feeds = facebook.getFeed(feedId);

            int size=feeds.size();

            //System.out.println("Fetching next "+size+"posts ");
            // For all xx feeds...
            for (int i = 0; i < size; i++) {
                // Get post.
                Post post = feeds.get(i);
                // Get (string) message.
                String message = post.getMessage();


                PagableList<Comment> comments = post.getComments();


                PagableList<Like> likes = post.getLikes();
                String id = post.getId();
                String json = DataObjectFactory.getRawJSON(post);
                System.out.println(json);
                postWriter.println(json);
                //System.out.println(comments);
                //System.out.println(likes);
                String parentId = id;
                for (int j = 0; j < comments.size(); j++) {
                    // Get post.
                    Comment comment = comments.get(j);

                    String msg = comment.getMessage();
                    String commentId = comment.getId();
                    String created_time = comment.getCreatedTime().toString();
                    Integer likeCount = comment.getLikeCount();
                    String from = comment.getFrom().getName();
                    String fromId = comment.getFrom().getId();
                    JSONObject obj = new JSONObject();
                    obj.put("parentId", parentId);
                    obj.put("id", commentId);
                    obj.put("message", msg);
                    obj.put("created_time", created_time);
                    obj.put("likeCount", likeCount);
                    obj.put("from", from);
                    obj.put("fromId", fromId);


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
                    obj.put("name", name);
                    obj.put("category", category);
                    likesWriter.println(obj.toString());

                }


            }

            postWriter.flush();
            commentWriter.flush();
            likesWriter.flush();
            Paging<Post> page = feeds.getPaging();
            feeds=facebook.fetchNext(page);
            if(feeds.size()<1)break;
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