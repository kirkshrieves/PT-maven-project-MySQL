package projects.service;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import projects.dao.DbConnection;
import projects.entity.Category;
import projects.entity.Material;
import projects.entity.Projects;
import projects.entity.Step;
import projects.exception.DbException;
import provided.util.DaoBase;

public class ProjectDao extends DaoBase {
	
	private static final String CATEGORY_TABLE = "category";
	private static final String MATERIAL_TABLE = "material";
	private static final String PROJECTS_TABLE = "projects";
	private static final String PROJECTS_CATEGORY_TABLE = "projects_category";
	private static final String STEP_TABLE = "step";

	public Projects insertProject(Projects project) {
		// @formatter:off
		String sql = ""
			+ "INSERT INTO " + PROJECTS_TABLE + " "
			+ "(projects_name, estimated_hours, actual_hours, difficulty, notes) "
			+ "VALUES "
			+ "(?, ?, ?, ?, ?)";
		// @formatter:on
		
		try(Connection conn = DbConnection.getConnection()) {
			startTransaction(conn);
			
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			setParameter(stmt, 1, project.getProjectName(), String.class);
			setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
			setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
			setParameter(stmt, 4, project.getDifficulty(), Integer.class);
			setParameter(stmt, 5, project.getNotes(), String.class);
			stmt.executeUpdate();
			Integer projectId = getLastInsertId(conn, PROJECTS_TABLE);
			commitTransaction(conn);
			project.setProjectId(projectId);
			return project;
		}
		catch(Exception e) {
			rollbackTransaction(conn);
			throw new DbException(e);
		  }
		}
		catch(SQLException e) {
			throw new DbException(e);
		}
	}

	public List<Projects> fetchAllProjects() {
		String sql = "SELECT * FROM " + PROJECTS_TABLE + " ORDER BY projects_name";
		try(Connection conn = DbConnection.getConnection()){
		   startTransaction(conn);
		   try(PreparedStatement stmt = conn.prepareStatement(sql)){
			  try(ResultSet rs = stmt.executeQuery()){
				  List<Projects> project = new LinkedList<>();
				  while(rs.next()) {
					  project.add(extract(rs, Projects.class));
//					  Projects projects = new Projects();
//					  
//					  ((Projects) project).setActualHours(rs.getBigDecimal("actual_hours"));
//					  ((Projects) project).setDifficulty(rs.getObject("difficulty", Integer.class));
//					  ((Projects) project).setEstimatedHours(rs.getBigDecimal("estimated_hours"));
//					  ((Projects) project).setNotes(rs.getString("notes"));
//					  ((Projects) project).setProjectId(rs.getObject("projects_id", Integer.class));
//					  ((Projects) project).setProjectName(rs.getString("projects_name"));
//					  
//					  projects.add(project);
				  }
				  return project;
			  }
		  } 
		  catch(Exception e) {
			  rollbackTransaction(conn);
			  throw new DbException(e);
		  }
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}

	public Optional<Projects> fetchProjectsById(Integer projectId) {
			String sql = "SELECT * FROM " + PROJECTS_TABLE + " WHERE projects_id = ?";
			try(Connection conn = DbConnection.getConnection()){
				startTransaction(conn);
				try {
					Projects project = null;
					try(PreparedStatement stmt = conn.prepareStatement(sql)){
						setParameter(stmt, 1, projectId, Integer.class);
						
						try(ResultSet rs = stmt.executeQuery()){
							if(rs.next()) {
								project = extract(rs, Projects.class);
							}
						}
					}
			     if(Objects.nonNull(project)) {
			    	 project.getMaterials().addAll(fetchMaterialsForProject(conn, projectId));
			    	 project.getSteps().addAll(fetchStepsForProject(conn, projectId));
			    	 project.getCategories().addAll(fetchCategoriesForProject(conn, projectId));
			     }
			     
			     //commitTransaction(conn);
			     return Optional.ofNullable(project);
				}
			catch(Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			}
			catch(SQLException e) {
				throw new DbException(e);
			}
	}

	private List<Category> fetchCategoriesForProject(Connection conn, Integer projectId) {
		// @formatter:off
		String sql = "" + "SELECT c.* FROM " + CATEGORY_TABLE + " c " + "JOIN " + PROJECTS_CATEGORY_TABLE + " pc USING (category_id) " + " WHERE projects_id = ?";
		// @formatter:on
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			setParameter(stmt, 1, projectId, Integer.class);
			try(ResultSet rs = stmt.executeQuery()){
				List<Category> categories = new LinkedList<>();
				while(rs.next()) {
					categories.add(extract(rs, Category.class));
				}
				return categories;
			}
		}
		catch(SQLException e) {
			throw new DbException(e);
		}
	}

	private List<Step> fetchStepsForProject(Connection conn, Integer projectId) {
		String sql = "SELECT * FROM " + STEP_TABLE + " WHERE projects_id = ?";
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			setParameter(stmt, 1, projectId, Integer.class);
			try(ResultSet rs = stmt.executeQuery()){
				List<Step> steps = new LinkedList<>();
				while(rs.next()) {
					steps.add(extract(rs, Step.class));
				}
				return steps;
			}
		}
		catch(SQLException e) {
			throw new DbException(e);
		}
	}

	private List<Material> fetchMaterialsForProject(Connection conn, Integer projectId) {
		String sql = "SELECT * FROM " + MATERIAL_TABLE + " WHERE projects_id = ?";
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			setParameter(stmt, 1, projectId, Integer.class);
			try(ResultSet rs = stmt.executeQuery()){
				List<Material> materials = new LinkedList<>();
				while(rs.next()) {
					materials.add(extract(rs, Material.class));
				}
				return materials;
			}
		}
		catch(SQLException e) {
			throw new DbException(e);
		}
	}

	public boolean modifyProjectDetails(Projects project) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
