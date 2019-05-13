package com.insigma.modules;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.insigma.facade.openapi.vo.ProjectDTO;
import com.insigma.mapper.ProjectMapper;
import com.insigma.po.Project;

import star.vo.BaseVo;

@Service
public class ProjectService{
	private static Logger logger = LoggerFactory.getLogger(ProjectService.class);

	@Resource
	ProjectMapper projectMapper;
	
	public ProjectDTO getByPrimaryKey(Long id) {
		Project po = projectMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(ProjectDTO.class);
	}

	public List<ProjectDTO> getProjectList(HashMap<String, Object> searchMap,int start,int size) {
		if(size<1) {
			logger.info("ProjectImpl.getProjectList searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<Project> list = projectMapper.getProjectList(searchMap, new RowBounds(start, size));
		if(CollectionUtils.isEmpty(list)) return Collections.emptyList();
		return BaseVo.copyListTo(list, ProjectDTO.class);
	}

	public List<ProjectDTO> getProjectList(HashMap<String, Object> searchMap) {
		List<Project> list = projectMapper.getProjectList(searchMap);
		if(CollectionUtils.isEmpty(list)) return Collections.emptyList();
		return BaseVo.copyListTo(list, ProjectDTO.class);
	}

	public int insertProject(ProjectDTO projectDTO) {
		if(projectDTO == null) {
			logger.info("ProjectService.insertProject dto={}",projectDTO);
		}
		return projectMapper.insertProject(projectDTO.copyTo(Project.class));
	}

	public int updateProject(ProjectDTO projectDTO) {
		if(projectDTO == null || projectDTO.getId()==null || projectDTO.getId()==0 ) {
			logger.info("ProjectService.updateProject dto={}",projectDTO);
		}
		return projectMapper.updateProject(projectDTO.copyTo(Project.class));
	}

	public int deleteProject(ProjectDTO projectDTO) {
		if(projectDTO == null || projectDTO.getId()==null || projectDTO.getId()==0 ) {
			logger.info("ProjectService.deleteProject dto={}",projectDTO);
		}
		return projectMapper.deleteProject(projectDTO.copyTo(Project.class));
	}

}
