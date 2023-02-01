package projects.service;

import java.util.List;
import java.util.NoSuchElementException;

import projects.entity.Projects;
import projects.exception.DbException;

public class ProjectService{
	
	
	private ProjectDao projectDao = new ProjectDao();

	public Projects addProject(Projects project) {
		return projectDao.insertProject(project);
	}

	public List<Projects> fetchAllProjects() {
		return projectDao.fetchAllProjects();
	}

	public Projects fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectsById(projectId).orElseThrow(() -> new NoSuchElementException("Project with project ID =" + projectId + " does not exist."));
		}

	public void modifyProjectDetails(Projects project) {
		if(!projectDao.modifyProjectDetails(project)) {
			throw new DbException("Project with ID=" + project.getProjectId() + " does not exist.");
		}
		
	}

	public void deleteProject(Integer projectsId) {
		if(!projectDao.deleteProject(projectsId)) {
			throw new DbException("Project with ID=" + projectsId + " does not exist.");
		}
		
	}
}
