package com.myezen.myapp.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.PageMaker;
import com.myezen.myapp.domain.SearchCriteria;
import com.myezen.myapp.service.BoardService;
import com.myezen.myapp.util.MediaUtils;
import com.myezen.myapp.util.UploadFileUtiles;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired//(required=false)오류가 나타나도 실행해라
	BoardService bs;
	
	@Autowired(required = false)//이값이 NULL이여도 실행하게해라//주입받는다 받는객체는 @Component이다
	PageMaker pm;
	
	@Resource(name = "uploadPath") //@Autowired같이 다른방법 @Resource id값으로 찾는다  @Autowired는 타입으로 찾는다
	String uploadPath;
	
	
	
//!게시판목록페이지
	@RequestMapping(value = "/boardList.do")
	public String boardList(Model model,SearchCriteria scri) {//SearchCriteria 매개변수로 받는다 주입받는다
		int totalCount = bs.boardTotal(scri); //총 게시물 갯수 꺼내오기
		pm.setScri(scri);
		pm.setTotalCount(totalCount);
		
		
		ArrayList<BoardVo> blist = bs.boardSelectAll(scri); //게시물다가져오기
		
		model.addAttribute("blist", blist);
		model.addAttribute("pm", pm);
		
		return "/board/boardList";
	}
//!게시판글보기페이지
	@RequestMapping(value = "/boardContents.do")
	public String boardContents(Model model, @RequestParam("bidx") int bidx){
		
		int value = bs.boardViewCnt(bidx);//게시물조회수
		BoardVo bv = bs.boardSelectOne(bidx);//게시물하나보기
		
		model.addAttribute("bv", bv);
		
		return "/board/boardContents";
	}

	//!글보기에서 파일다운로드
	@ResponseBody
	@RequestMapping(value="/displayFile.do", method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String fileName,@RequestParam(value="down",defaultValue="0" ) int down ) throws Exception{
		
	//	System.out.println("fileName:"+fileName);
		
		InputStream in = null;//InputStream은 데이터를 byte 단위로 읽어들이는 통로이며 (읽어들인 데이터를 byte로 돌려줌)		
		ResponseEntity<byte[]> entity = null;//에러 코드와 같은 HTTP상태 코드를 전송하고 싶은 데이터와 함께 전송할 수 있기 때문에 좀 더 세밀한 제어가 필요한 경우 사용한다고 합니다.
		
	//	logger.info("FILE NAME :"+fileName);
		
		try{
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();//HTTP 요청 또는 응답 헤더를 나타내는 데이터 구조로, 문자열 헤더 이름을 문자열 값 목록에 매핑하고 일반적인 애플리케이션 수준 데이터 유형에 대한 접근자를 제공합니다.	
			 
			in = new FileInputStream(uploadPath+fileName);
			/*FileInputStream : InputStream 클래스를 상속받은 자식 클래스, 하드 디스크 상에 있는 파일로부터 바이트 단위의 입력을 받는 클래스다. 출발 지점과 도착 지점을 연결하는 통로(스트림)을 생성한다.
			생성자의 인자로 File 객체를 주거나 파일명을 직접 String 형태로 줄 수 있다. 일반적으로 파일명을 String 꼴로 주는 경우가 많은데 파일이 존재하지 않을 가능성도 있어 Exception 처리가 필요.*/
			
			
			if(mType != null){
				
				if (down==1) {
					fileName = fileName.substring(fileName.indexOf("_")+1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.add("Content-Disposition", "attachment; filename=\""+
							new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");	
					
				}else {
					headers.setContentType(mType);	
				}
				
			}else{
				
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""+
						new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");				
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
					headers,
					HttpStatus.CREATED);
			
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally{
			in.close();
		}
		return entity;
	}
//!게시판글쓰기페이지
	@RequestMapping(value = "/boardWrite.do")
	public String boardWrite() {
		
		
		return "/board/boardWrite";
	}
	//!게시판글쓰기페이지에서 글쓰기 버튼 누르면 //!게시판 파일업로드
		@RequestMapping(value = "/boardWriteAction.do")
		public String boardWriteAction(
				@RequestParam("subject") String subject,
				@RequestParam("contents") String contents,
				@RequestParam("writer") String writer,
				@RequestParam("pwd") String pwd,
				@RequestParam("filename") MultipartFile filename,
				HttpSession session
				//클라이언트 ip뽑아내는 방식으로 차후에 바꾼다
				) throws IOException, Exception {
			
				MultipartFile file = filename;
				System.out.println("원본파일이름"+file.getOriginalFilename());
				String uploadedFileName="";
				if (!file.getOriginalFilename().equals("")) {
					uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());//위치,오리지널이름,파일형식
				}
				String ip = InetAddress.getLocalHost().getHostAddress();
				int midx=0;
				if (session.getAttribute("midx")!=null) {
					midx = Integer.parseInt(session.getAttribute("midx").toString());					
				}
				//다른방식으로 써봄
				BoardVo bv = new BoardVo();//한번에 담아서 보내기
				bv.setSubject(subject);
				bv.setContents(contents);
				bv.setWriter(writer);
				bv.setIp(ip);
				bv.setMidx(midx);
				bv.setPwd(pwd);//게시판-삭제-비밀번호추가
				bv.setFilename(uploadedFileName);//게시판-파일업로드
				
				int value = bs.boardInsert(bv);

			return "redirect:/board/boardList.do";
		}
 
//!게시판글수정하기페이지
	@RequestMapping(value = "/boardModify.do")
	public String boardModify(Model model, @RequestParam("bidx") int bidx) {
		
		BoardVo bv = bs.boardSelectOne(bidx);//게시물하나보기
		
		model.addAttribute("bv", bv);
		
		return "/board/boardModify";
	}
	//!게시판글수정하기페이지에서 글수정하기 버튼 누르면
		@RequestMapping(value = "/boardModifyAction.do")
		public String boardModifyAction(
				@RequestParam("bidx") int bidx,
				@RequestParam("subject") String subject,
				@RequestParam("contents") String contents,
				@RequestParam("writer") String writer,
				@RequestParam("pwd") String pwd,
				@RequestParam("filename") MultipartFile filename,
				HttpSession session
				)throws IOException, Exception {
			
			MultipartFile file = filename;
			System.out.println("원본파일이름"+file.getOriginalFilename());
			String uploadedFileName="";
			if (!file.getOriginalFilename().equals("")) {
				uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());//위치,오리지널이름,파일형식
			}
			String ip = InetAddress.getLocalHost().getHostAddress();
			int midx=0;
			if (session.getAttribute("midx")!=null) {
				midx = Integer.parseInt(session.getAttribute("midx").toString());			
			}
			//다른방식으로 써봄
			BoardVo bv = new BoardVo();//한번에 담아서 보내기
			bv.setBidx(bidx);
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setIp(ip);
			bv.setMidx(midx);
			bv.setPwd(pwd);//게시판-삭제-비밀번호추가
			bv.setFilename(uploadedFileName);//게시판-파일업로드
			
			int value = bs.boardModify(bv);
			String path="";
			if (value == 1) {
				path = "redirect:/board/boardContents.do?bidx="+bidx;
			}
			else {
				path = "redirect:/board/boardModify.do.do?bidx="+bidx;
			}
			
			return path;
		}
//!게시판글삭제하기페이지
	@RequestMapping(value = "/boardDelete.do")
	public String boardDelete(
			@RequestParam("bidx") int bidx,
			Model model
			) {
		BoardVo bv = bs.boardSelectOne(bidx);//게시물하나보기
		model.addAttribute("bv", bv);
		
		return "/board/boardDelete";
	}
	//!게시판글삭제하기페이지에서 글삭제하기 버튼 누르면
		@RequestMapping(value = "/boardDeleteAction.do")
		public String boardDeleteAction(
				@RequestParam("bidx") int bidx,
				@RequestParam("pwd") String pwd
				) {
			BoardVo bv = new BoardVo();//한번에 담아서 보내기
			bv.setBidx(bidx);
			bv.setPwd(pwd);
			
			int value  = bs.boardDelete(bv);
			String path="";
			if (value == 1) {
				path = "redirect:/board/boardList.do";
			}else {
				path = "redirect:/board/boardDelete.do?bidx="+bidx;
			}	
			
			return path;
		}
//!게시판글답변하기페이지
	@RequestMapping(value = "/boardReply.do")
	public String boardReply(
			@RequestParam("bidx") int bidx,
			@RequestParam("originbidx") int originbidx,
			@RequestParam("depth") int depth,
			@RequestParam("level_") int level_,
			Model model
			) {
		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		bv.setOriginbidx(originbidx);
		bv.setDepth(depth);
		bv.setLevel_(level_);
		model.addAttribute("bv", bv);
		
		return "/board/boardReply";
	}
	//!게시판글답변하기페이지에서 답변하기누르면
	@RequestMapping(value = "/boardReplyAction.do")
	public String boardReplyAction(
			@RequestParam("bidx") int bidx,
			@RequestParam("originbidx") int originbidx,
			@RequestParam("depth") int depth,
			@RequestParam("level_") int level_,
			@RequestParam("subject") String subject,
			@RequestParam("contents") String contents,
			@RequestParam("writer") String writer,
			@RequestParam("pwd") String pwd,
			@RequestParam("filename") MultipartFile filename,
			HttpSession session,
			Model model
			)throws IOException, Exception {
		
		MultipartFile file = filename;
		System.out.println("원본파일이름"+file.getOriginalFilename());
		String uploadedFileName="";
		if (!file.getOriginalFilename().equals("")) {
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());//위치,오리지널이름,파일형식
		}
		String ip = InetAddress.getLocalHost().getHostAddress();
		int midx=0;
		if (session.getAttribute("midx")!=null) {
			midx = Integer.parseInt(session.getAttribute("midx").toString());				
		}
		//다른방식으로 써봄
		BoardVo bv = new BoardVo();//한번에 담아서 보내기
		bv.setOriginbidx(originbidx);
		bv.setDepth(depth);
		bv.setLevel_(level_);
		bv.setSubject(subject);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setPwd(pwd);
		bv.setIp(ip);
		bv.setMidx(midx);
		bv.setFilename(uploadedFileName);//게시판-파일업로드
		
		int value  = bs.boardReply(bv);
		
		String path="";
		if (value == 1) {
			path = "redirect:/board/boardList.do";
		}else {
			path = "redirect:/board/boardReply.do?bidx="+bidx;
		}	
		
		return path;

	}


}//컨트롤러 끝
