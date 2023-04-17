package com.document.pdf.entity;

import javax.persistence.*;

@Entity
@Table(name = "documents")
public class DocumentInfo {

    @Id
    @Column(name = "doc_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "doc_name")
    private String documentName;

    @Column(name = "document_path")
    private String path;

    @Column(name = "post_date")
    private String postDate;

    @Column(name = "posts")
    private String posts;

    @Column(name = "comments")
    private String comments;

    public DocumentInfo(){

    }

    public DocumentInfo(long id, String documentName, String path, String postDate, String posts, String comments) {
        this.id = id;
        this.documentName = documentName;
        this.path = path;
        this.postDate = postDate;
        this.posts = posts;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
