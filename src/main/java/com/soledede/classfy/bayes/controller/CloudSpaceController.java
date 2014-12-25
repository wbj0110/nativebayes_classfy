package com.soledede.classfy.bayes.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soledede.classfy.bayes.model.Folder;
import com.soledede.classfy.bayes.model.User;

/**
 * A rest controller representing a cloud based filesystem. You can access your
 * filesystem via rest api and ... - list all root folders (GET /cloudspace) -
 * access folders with their subfolders (GET /cloudspace/media) - add new
 * subfolders (POST /cloudspace/documents/tutorials) - remove subfolders (DELETE
 * /cloudspace/documents/tutorials)
 *
 * @author wengbenjue
 */
@RestController
@RequestMapping(value = "/cloudspace", produces = "application/json;charset=UTF-8")
public class CloudSpaceController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<Folder> listRootFolders() {
		return VFS_MOCK.values();
	}

	@RequestMapping(value = "/{folderName}", method = RequestMethod.GET)
	public Folder getFolder(@PathVariable String folderName) {
		return VFS_MOCK.get(folderName);
	}

	@RequestMapping(value = "/{folderName}/{subFolderName}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public boolean addSubFolder(@PathVariable String folderName,
			@PathVariable String subFolderName) {
		return getFolder(folderName).add(new Folder(subFolderName));
	}

	@RequestMapping(value = "/{folderName}/{subFolderName}", method = RequestMethod.DELETE)
	public boolean deleteSubFolder(@PathVariable String folderName,
			@PathVariable String subFolderName) {
		return getFolder(folderName).delete(new Folder(subFolderName));
	}

	// 设置RequestMapping的headers参数标明请求体的内容是json数据
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Content-Type=application/json")
	// @ResponseStatus表示响应状态 NO_CONTENT表示无响应内容
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// 没有配置ViewResolver的情况下@ResponseBody不能省略,否则404
	// @RequestBody表示将请求体的数据转为user类
	public void addUser(@RequestBody User user) {
		System.out.println(user.getUsername());
	}

	static final Map<String, Folder> VFS_MOCK = new HashMap<String, Folder>();
	static {
		VFS_MOCK.put("media", new Folder("中文", "music", "photos", "videos"));
		VFS_MOCK.put("documents", new Folder("documents", "recipies", "diary"));
		VFS_MOCK.put("projects", new Folder("projects"));
	}

}
