package edu.duke.scholars.middleware.graphql.inputmodel;

public class CreatePersonInput {
 
  private String id;
  private String name;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  // type Educations or EducationInput -- ?
  // or just part of personInput ??? 
  // possible to do something like this?
  // public void setEducatinos(ArrayList<Education> dducations) {
  //  this.educations = educations;
  //}

  //public ArrayList<Educations> getEducations() {
  //  return this.educations;
  //}

}
