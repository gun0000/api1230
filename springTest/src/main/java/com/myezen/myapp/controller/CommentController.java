package com.myezen.myapp.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myezen.myapp.domain.CommentVo;
import com.myezen.myapp.service.CommentService;



@RestController //객체로 반환하는 컨트롤러
@RequestMapping(value = "/comment")
public class CommentController {
	
	@Autowired
	private CommentService cs;
	

	@RequestMapping(value = "/commentWrite.do", method = RequestMethod.POST)//POST로 넘겨받는다
	public JSONObject commentWrite(//반환값이 JSON타입인 commentWrite메소드 //HashMap<String, Object> 반환값이 해쉬맵도 쓸수 있음
			CommentVo cv //더 나은 방법 
			//@RequestParam("bidx") int bidx,//파라미터로 받는다 //처음에 한거 
			//@RequestParam("midx") int midx,
			//@RequestParam("cwriter") String cwriter,
			//@RequestParam("ccontents") String ccontents
			//@RequestParam("nextBlock") int nextBlock
			) throws Exception{
		String ip = InetAddress.getLocalHost().getHostAddress();//호스트 아이피
		
		//CommentVo cv = new CommentVo(); //처음에 한거 
		//cv.setBidx(bidx);
		//cv.setMidx(midx);
		//cv.setCwriter(cwriter);
		//cv.setCcontents(ccontents);
		cv.setIp(ip);
		
		//HashMap<String, Object> hm = new HashMap<String, Object>();//JSON으로 보내는것은 해쉬맵으로도 가능 //해결: 해쉬맵 hm 선언 안됨 안되는이유 객체생성을 안해서 
		int value = cs.commentInsert(cv);
		System.out.println("입력여부:"+value);
		//hm.put("value", value);//{value=1} 이런식으로 담김
		//System.out.println(hm);
		JSONObject js = new JSONObject();//JSON담는 객체 생성 [해쉬맵같다]
		js.put("value", value);//{"value":1} 이런식으로 담김
		System.out.println(js);
		
		return js;
	}
	
	@RequestMapping(value = "/{bidx}/{nextBlock}/commentList.do")//{bidx}로 받을수 있다  //RESTful API 방식
	public JSONObject commentList(@PathVariable("bidx") int bidx, @PathVariable("nextBlock") int nextBlock ){//@PathVariable 주소에 bidx로 넘어온것을 받아올수있다 //해쉬맵으로 할수있다 HashMap<String, Object>
		JSONObject js = new JSONObject(); //해쉬맵으로 사용가능
		ArrayList<CommentVo> alist = cs.commentSelectAll(bidx,nextBlock);
		//System.out.println("alist"+alist);
		
		int totalCnt = cs.commentTotalCnt(bidx);//전체갯수 뽑아오기 // 더보기 버튼
		
		String moreView = "N";
		
		if (totalCnt > nextBlock*15) {
			moreView = "Y";
		}
		
		/*
		HashMap<String, Object> hm = new HashMap<>(); //해쉬맵으로 하는 방법
		hm.put("alist", alist);// ArrayList로 나온값 alist을 해쉬맵 에 넣는다
		ArrayList<CommentVo> aalist = (ArrayList<CommentVo>)hm.get("alist"); //해쉬값에 넣은 alist를 꺼내서 ArrayList로 형변환을 해준다
		System.out.println(aalist.get(0).getBidx());//이런식으로 뽑아낼수 있다
		*/
		
		js.put("alist", alist);
		js.put("moreView", moreView);
		
		return js; 
	}
	
	@RequestMapping(value = "/{bidx}/{nextBlock}/more.do")//더보기 클릭시
	public JSONObject more(@PathVariable("bidx") int bidx, @PathVariable("nextBlock") int nextBlock) {
		JSONObject js = new JSONObject();
		ArrayList<CommentVo> alist = cs.commentSelectAll(bidx,nextBlock);	
		int totalCnt = cs.commentTotalCnt(bidx);//전체갯수 뽑아오기 // 더보기 버튼
		int nextBlock2 = 0;
		if (totalCnt > nextBlock*15) {
			nextBlock2 = nextBlock+1;
		}else {
			nextBlock2 = nextBlock;
		}
		
		
		js.put("alist", alist);
		js.put("nextBlock", nextBlock2);
		

		return js;
	}
	
	@RequestMapping(value = "/{bidx}/{cidx}/commentDelete.do")//더보기 클릭시
	public JSONObject commentDelete(@PathVariable("bidx") int bidx, @PathVariable("cidx") int cidx) {
		JSONObject js = new JSONObject();

		int value = cs.commentOneDelete(cidx);

		js.put("value", value);

		return js;
	}
	
	
	
	
	
	
	
	
	
	
}
