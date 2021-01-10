package Server;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ratingId;

    @ManyToOne(cascade = {CascadeType.ALL})
    private User userThatRated;

    private int nummericalRating;

    private String ratingComment;

    //Ein Rating gehört zu einem ITEM
    @ManyToOne(cascade = {CascadeType.ALL})
    private User userRated;

    public Rating(){}
    public Rating(int sterne,String comment){
        this.nummericalRating=sterne;
        this.ratingComment=comment;
    }


    public int getItemId() {
        return ratingId;
    }

    public void setItemId(int ratingId) {
        this.ratingId= ratingId;
    }

    public User getUserThatRated() {
        return userThatRated;
    }

    public void setUserThatRated(User userThatRated) {
        this.userThatRated = userThatRated;
    }

    public int getNummericalRating() {
        return nummericalRating;
    }

    public void setNummericalRating(int nummericalRating) {
        this.nummericalRating = nummericalRating;
    }

    public String getRatingComment() {
        return ratingComment;
    }

    public void setRatingComment(String ratingComment) {
        this.ratingComment = ratingComment;
    }

    public User getUserRated() {
        return userRated;
    }

    public void setUserRated(User iterm) {
        this.userRated = iterm;
    }


}
