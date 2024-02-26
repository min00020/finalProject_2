package com.yedamFinal.aco.question.serviceImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.common.PaginationDTO;
import com.yedamFinal.aco.common.ReplyJoinVO;
import com.yedamFinal.aco.common.mapper.ReplyMapper;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.mapper.MemberMapper;
import com.yedamFinal.aco.point.PointDetailVO;
import com.yedamFinal.aco.point.mapper.PointMapper;
import com.yedamFinal.aco.question.QuestionVO;
import com.yedamFinal.aco.question.mapper.QuestionMapper;
import com.yedamFinal.aco.question.service.QuestionService;
import com.yedamFinal.aco.question.web.QuestionActivityPointVO;

@Service
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private PointMapper pointMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	//질문글 리스트 조회
	@Override
	public void getQuestionList(Model model, int pageNo, String search) {
		var questionList = questionMapper.getQuestionList(pageNo, search);
		PaginationDTO dto = null;
		if(questionList.size() > 0) {
			dto = new PaginationDTO(questionMapper.getQuestionCount(search),pageNo,5);
		}
		
		model.addAttribute("pageDTO", dto);
		model.addAttribute("questionList", questionList);
		
		var mainRanking = questionMapper.mainRanking();
		model.addAttribute("mainRanking", mainRanking);
		
	}
	
	//질문글 리스트 분류 조회
	@Override
	public void getQuestionListTopic(Model model, int pageNo, String topic, String search) {
		var questionListTopic = questionMapper.getQuestionListTopic(pageNo, topic, search);
		PaginationDTO dto = null;
		if(questionListTopic.size() > 0) {
			dto = new PaginationDTO(questionMapper.getQuestionTopicCount(topic, search),pageNo,5);
		}
		
		model.addAttribute("pageDTO", dto);
		model.addAttribute("questionList", questionListTopic);
	}

	//단건조회
	@Override
	public Map<Integer, List<QuestionVO>> getQuestionInfo(int qno, Model model, int memberNo) {
		//조회수 +1
		questionMapper.updateQuestionViewCnt(qno);
		
		//북마크 조회
				MybookmarkVO bookmarkvo= questionMapper.questionBookmarkInfo(memberNo, qno);
				
				if(bookmarkvo == null || bookmarkvo.getTitle() == null) {
					model.addAttribute("isCheckBookmark", 0);
				}
				else {
					model.addAttribute("isCheckBookmark", 1);
				}
				
		List<QuestionVO> result = questionMapper.getQuestionInfo(qno);
		Map<Integer, List<QuestionVO>> questionMap 
			= result.stream().collect(Collectors.groupingBy(QuestionVO::getAnswerBoardNo));
		
		//번호 boardNo 기준 > 0부터 시작하게 변경
		Map<Integer, List<QuestionVO>> ret = new HashMap<Integer, List<QuestionVO>>();
		Integer idx = 0;
		for ( Map.Entry<Integer, List<QuestionVO>> entry : questionMap.entrySet() ) {
		    ret.put(idx++, entry.getValue());
		}
		
		//답변 채택상태 확인
		model.addAttribute("isAdopt",false);
		for ( Map.Entry<Integer, List<QuestionVO>> entry : questionMap.entrySet() ) {
			System.out.println("value : " + entry.getValue());
			List<QuestionVO> adoptStatus = entry.getValue();
			for(QuestionVO vo : adoptStatus) {
				if(vo.getAnswerAdoptStatus() == null) {
					continue;
				}
				
				//채택답변 AdoptQuestionVO에 담아주기
				if(vo.getAnswerAdoptStatus().equals("I002")) {
					model.addAttribute("isAdopt",true);
					for(Map.Entry<Integer, List<QuestionVO>> myEntry : questionMap.entrySet()) {
		                  List<QuestionVO> list = myEntry.getValue();
		                  var findData = list.stream().filter(questionVO -> questionVO.getAnswerAdoptStatus().equals("I002")).findFirst();
		                  if(findData.isPresent()) {
		                     model.addAttribute("AdoptQuestionVO", findData.get());
		                     Collections.sort(list, new Comparator<QuestionVO>() {
		                    	 public int compare(QuestionVO o1, QuestionVO o2) {
		                    	  	return o1.getAddWriteDate().compareTo(o2.getAddWriteDate());
		                    	 }
		                     }); 
		                     var lastVO = list.get(list.size()-1);
		                     
		                     if(lastVO.getAddWriterType() != null) {
		                    	 //질문자 J001 답변자 J002
		                    	 if(lastVO.getAddWriterType().equals("J001")) {
		                    		 model.addAttribute("currentWriterType", 1);
		                    	 }
		                    	 else {
		                    		 model.addAttribute("currentWriterType", 2);
		                    	 }
		                     }
		                     break;
		                  }
		             }
					 break;
				}
			}
		}
		
		//댓글
		List<ReplyJoinVO> list = replyMapper.selectReply("N001", qno);
		Map<Integer, List<ReplyJoinVO>> groupByData = list.stream().collect(Collectors.groupingBy(ReplyJoinVO::getParentReplyNo));
		groupByData = groupByData.entrySet().stream()
		        .sorted(Map.Entry.comparingByKey())
		        .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (a, b) -> { throw new AssertionError(); },
		                LinkedHashMap::new
		        ));

		model.addAttribute("replyList", groupByData);
		
		//로그인 유저의 답변글 작성 여부 체크
		model.addAttribute("writePost",false);
		for( Map.Entry<Integer, List<QuestionVO>> entry : questionMap.entrySet() ) {
			List<QuestionVO> writeStatus = entry.getValue();
			for(QuestionVO vo : writeStatus) {
				if(vo.getAnswerMemberNo() == memberNo) {
					model.addAttribute("writePost", true);
					System.out.print("test22");
					break;
				}
			}
		}
		return ret;
	}
	

	//질문글 단건조회 북마크
	@Transactional
	@Override
	public Map<String, Object> updateBookmark(int qno, int memberNo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		List<QuestionVO> result = questionMapper.getQuestionInfo(qno);
		int bookmarkCnt = result.get(0).getBookmarkCnt();

		ret.put("result", "200");
		if(result.size() <= 0) {
			ret.put("result", "400");
		}
		else {
			MybookmarkVO myBookmark = questionMapper.questionBookmarkInfo(memberNo, qno);
			MybookmarkVO bookmarkvo = new MybookmarkVO();
			//북마크 없는 경우 , 있는 경우
			if(myBookmark == null || myBookmark.getTitle() == null) {
				bookmarkvo.setMemberNo(memberNo);
				bookmarkvo.setQuestionBoardNo(qno);
				bookmarkvo.setRegistDate(new Date());
				bookmarkvo.setTitle(result.get(0).getTitle());
				questionMapper.insertBookmark(bookmarkvo);
				questionMapper.updateBookmarkCnt(0, qno);
				bookmarkCnt += 1;
			}
			else {
				questionMapper.updateBookmarkCnt(2, qno);
				questionMapper.deleteBookmark(qno);
				bookmarkCnt -= 1;
			}
		}
		ret.put("bookmarkCnt", bookmarkCnt);
		
		return ret;
	}
	
	
	//질문글작성
	@Transactional
	@Override
	public Map<String, Object> writeQuestion(QuestionVO vo, MemberVO mvo) {
		
		//질문글 작성
		Map<String,Object> ret = new HashMap<String,Object>();
		int insertId = questionMapper.insertQuestion(vo);
		int bno = vo.getPk();
		mvo.setPoint(vo.getPoint());
		
		/*활동점수 내역 업데이트*/
		QuestionActivityPointVO activityPointVO = new QuestionActivityPointVO();
		
		activityPointVO.setMemberNo(mvo.getMemberNo());
		activityPointVO.setAccumActivityPoint(mvo.getAccumActivityPoint());
		activityPointVO.setCurActivityPoint(mvo.getAvailableActivityPoint());
		activityPointVO.setActivityPointType("C001");
		activityPointVO.setActivityPointDate(new Date());
		activityPointVO.setIncDecActivityPoint(50);
		
		questionMapper.updateActivityPoint(activityPointVO);
		
		/*포인트 차감 insert*/
		PointDetailVO pointVO = new PointDetailVO();
		pointVO.setMemberNo(mvo.getMemberNo());
		pointVO.setLatestAcoMoney(mvo.getAcoMoney());
		pointVO.setLatestAcoPoint(mvo.getAcoPoint());
		pointVO.setLatestTotalPoints(mvo.getAcoMoney() + mvo.getAcoPoint());
		pointVO.setHistoryDate(new Date());
		pointVO.setHistoryType("F008");
	
		if(mvo.getAcoPoint()-vo.getPoint() < 0) {
			//point 내역 테이블에 point, money 두 번 인서트해주기
			if(mvo.getAcoPoint() > 0) {
				//사용하려는 포인트보다 보유 에코포인트가 적은 경우
				//에코포인트 전체 사용 후 + 에코머니로 나머지 차감
				pointVO.setPointType("G002");
				pointVO.setIncDecPoint(mvo.getAcoPoint() * -1);
				
				//에코머니 차감
				PointDetailVO pointVO2 = new PointDetailVO();
				pointVO2.setMemberNo(mvo.getMemberNo());
				pointVO2.setLatestAcoMoney(mvo.getAcoMoney());
				pointVO2.setLatestAcoPoint(mvo.getAcoPoint());
				pointVO2.setLatestTotalPoints(mvo.getAcoMoney() + mvo.getAcoPoint());
				pointVO2.setHistoryDate(new Date());
				pointVO2.setHistoryType("F008");
				pointVO2.setPointType("G001");
				pointVO2.setIncDecPoint((vo.getPoint() - mvo.getAcoPoint()) * -1);

				pointMapper.insertAcoMoneyHistory(pointVO2);
			}
			else {
				//인서트 에코머니만 한 번
				pointVO.setPointType("G001");
				pointVO.setIncDecPoint(vo.getPoint() * -1);
			}
		}
		else {
			//인서트 point에 한번만
			pointVO.setPointType("G002");
			pointVO.setIncDecPoint(vo.getPoint() * -1);
		}

		pointMapper.insertAcoMoneyHistory(pointVO);
		
		if(insertId <= 0) {
			ret.put("result", "500");
		}
		else {
			ret.put("result", "200");
			ret.put("vo", vo);
			ret.put("bno", bno);
			questionMapper.updatePoint(mvo);
		}
		
		return ret;
	}
	
	//질문글 수정
	@Override
	public Map<String, Object> modifyQuestion(QuestionVO vo) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", "200");
		
		int result = questionMapper.updateQuestion(vo);
		if(result <= 0) {
			ret.put("result", "500");
			return ret;
		}
		
		return ret;
	}

	@Override
	public QuestionVO deleteQuestion(int qno) {
		return questionMapper.deleteQuestion(qno);
	}
	
	//답변글 작성
	@Transactional
	@Override
	public Map<String, Object> writeAnswer(QuestionVO vo, MemberVO mvo) {
		//활동점수+50 내역
		QuestionActivityPointVO activityPointVO = new QuestionActivityPointVO();
		
		activityPointVO.setMemberNo(mvo.getMemberNo());
		activityPointVO.setAccumActivityPoint(mvo.getAccumActivityPoint());
		activityPointVO.setCurActivityPoint(mvo.getAvailableActivityPoint());
		activityPointVO.setActivityPointType("C002");
		activityPointVO.setActivityPointDate(new Date());
		activityPointVO.setIncDecActivityPoint(50);
		
		questionMapper.updateActivityPoint(activityPointVO);
		
		//답변글 작성
		Map<String,Object> ret = new HashMap<String,Object>();
		
		int insertId = questionMapper.insertAnswer(vo);
		if(insertId <= 0) {
			ret.put("result", "500");
		}
		else {
			ret.put("result", "200");
			//답변수+1
			questionMapper.plusAnswerCnt(vo.getQuestionBoardNo());
			questionMapper.updateAnsWritePoint(mvo);
			
			
		}
		return ret;
	}
	
	//답변글 수정
	@Override
	public Map<String, Object> modifyAnswer(QuestionVO vo) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", "200");
		
		int result = questionMapper.updateAnswer(vo);
		if(result <= 0) {
			ret.put("result", "500");
			return ret;
		}
		
		return ret;
	}
	
	//답변글 채택
	@Transactional
	@Override
	public int adoptAnswer(int ano, MemberVO mvo) {
		//답변 채택
		QuestionVO vo = questionMapper.selectAdoptAnswer(ano);
		
		mvo.setMemberNo(vo.getMemberNo());
		mvo = memberMapper.selectMemberInfo(mvo);
		
		mvo.setPoint(vo.getPoint());
		
		//활동점수 내역 업데이트
		QuestionActivityPointVO activityPointVO = new QuestionActivityPointVO();
		
		activityPointVO.setMemberNo(mvo.getMemberNo());
		activityPointVO.setAccumActivityPoint(mvo.getAccumActivityPoint());
		activityPointVO.setCurActivityPoint(mvo.getAvailableActivityPoint());
		activityPointVO.setActivityPointType("C003");
		activityPointVO.setActivityPointDate(new Date());
		activityPointVO.setIncDecActivityPoint(20);
		
		questionMapper.updateActivityPoint(activityPointVO);

		//포인트 내역 업데이트
		PointDetailVO pointVO = new PointDetailVO();
		pointVO.setMemberNo(mvo.getMemberNo());
		pointVO.setLatestAcoMoney(mvo.getAcoMoney());
		pointVO.setLatestAcoPoint(mvo.getAcoPoint());
		pointVO.setLatestTotalPoints(mvo.getAcoMoney() + mvo.getAcoPoint());
		pointVO.setHistoryDate(new Date());
		pointVO.setHistoryType("F004");
		pointVO.setPointType("G002"); //에코포인트 G002
		pointVO.setIncDecPoint(mvo.getPoint());
				
		pointMapper.insertAcoMoneyHistory(pointVO);
		
		//활동점수, 포인트 지급
		questionMapper.updateAdoptPoint(mvo);
		
		return questionMapper.adoptAnswer(ano);
	}
	
	//추가질문답변 작성
	@Override
	public Map<String, Object> writeQuestionAdd(QuestionVO vo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		int insertId = questionMapper.insertQuestionAdd(vo);
		if(insertId <= 0) {
			ret.put("result", "500");
		}
		else {
			ret.put("result", "200");
		}
		return ret;
	}
	//추가질문답변 채택
	@Override
	public int adoptAddAnswer(int questionAddNo) {
		return questionMapper.adoptAddAnswer(questionAddNo);
	}
}
