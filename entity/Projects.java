/**
 * 
 */
package projects.entity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Promineo
 *
 */
public class Projects {
  private Integer projectsId;
  private String projectsName;
  private BigDecimal estimatedHours;
  private BigDecimal actualHours;
  private Integer difficulty;
  private String notes;
  

  private List<Material> materials = new LinkedList<>();
  private List<Step> steps = new LinkedList<>();
  private List<Category> categories = new LinkedList<>();
  
 

  public Integer getProjectId() {
	  System.out.println(projectsId);
    return projectsId;
  }

  public void setProjectId(Integer projectId) {
	  System.out.println(projectId);
	  this.projectsId = projectId;
  }

  public String getProjectName() {
	  System.out.println(projectsName);
	  return projectsName;
  }

  public void setProjectName(String projectName) {
	  System.out.println(projectName);
	  this.projectsName = projectName;
  }

  public BigDecimal getEstimatedHours() {
    return estimatedHours;
  }

  public void setEstimatedHours(BigDecimal estimatedHours) {
    this.estimatedHours = estimatedHours;
  }

  public BigDecimal getActualHours() {
    return actualHours;
  }

  public void setActualHours(BigDecimal actualHours) {
    this.actualHours = actualHours;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public List<Material> getMaterials() {
    return materials;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public List<Category> getCategories() {
    return categories;
  }

  @Override
  public String toString() {
    String result = "";
    
    result += "\n   ID=" + projectsId;
    result += "\n   name=" + projectsName;
    result += "\n   estimatedHours=" + estimatedHours;
    result += "\n   actualHours=" + actualHours;
    result += "\n   difficulty=" + difficulty;
    result += "\n   notes=" + notes;
    
    result += "\n   Materials:";
    
    for(Material material : materials) {
      result += "\n      " + material;
    }
    
    result += "\n   Steps:";
    
    for(Step step : steps) {
      result += "\n      " + step;
    }
    
    result += "\n   Categories:";
    
    for(Category category : categories) {
      result += "\n      " + category;
    }
    
    return result;
  }

public void add(List<Projects> project) {
	
	
}
}
