package com.yonyou.cloud.ops.notify.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.controller.BaseController;
import com.yonyou.cloud.ops.notify.entity.NotifyThirdMessage;
import com.yonyou.cloud.ops.notify.service.NotifyThirdMessageService;

import net.sf.json.JSONObject;

@RestController()
@RequestMapping("notify")
public class NotifyThirdMessageController extends BaseController<NotifyThirdMessageService, NotifyThirdMessage>{

	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private NotifyThirdMessageService notifyThirdMessageService;
	
	@RequestMapping(value="thirdapi/message/{appId}/{bizId}",method=RequestMethod.GET)
	@YcApi
	public RestResultResponse<NotifyThirdMessage> getNotification(@RequestParam("appId") String appId,
			@RequestParam("bizId") String bizId)
			throws Exception {
		logger.info("--查询消息,appId="+appId+",bizId="+bizId);
		//根据appid和bizId查询消息状态
		List<NotifyThirdMessage> list=notifyThirdMessageService.getNotifyMessage(appId, bizId);
		logger.info("--查询消息,message size="+list.size());
		return new RestResultResponse<>().success(true).data(list);
	}
	
	@RequestMapping(value="thirdapi/message/resend",method=RequestMethod.GET)
	@YcApi
	public RestResultResponse<Map<String,String>> reSendMessage()
			throws Exception {
		logger.info("--开始重发消息");
		//重新发送消息
		notifyThirdMessageService.reSendMessage();
		Map<String,String> map=new HashMap();
		map.put("resultcode", "ok");
		map.put("message", "重发成功");
		return new RestResultResponse<>().success(true).data(map);
	}
	
	@RequestMapping(value="thirdapi/demo",method=RequestMethod.POST)
	@YcApi
	public RestResultResponse<Map<String,String>> receiveMessage(@RequestBody String message,
			@RequestParam("bizId") String bizId)
			throws Exception {
		if(message==null){
			logger.info("--message=null,bizId="+bizId);
		}else{
			logger.info("--message="+message+",bizId="+bizId);
		}
		
		Map<String,String> map=new HashMap();
		map.put("resultcode", "1001002");
		map.put("message", "成功");
		return new RestResultResponse<>().success(true).data(map);
	}
	
	
	
	
}
