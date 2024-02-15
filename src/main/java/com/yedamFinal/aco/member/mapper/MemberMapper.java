package com.yedamFinal.aco.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.activity.ActivityPointVO;
import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.common.TagVO;
import com.yedamFinal.aco.member.FindAccountEmailLinkVO;
import com.yedamFinal.aco.member.MemberQuestionChartVO;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.myemoticon.MyemoticonVO;
import com.yedamFinal.aco.point.AccountVO;
import com.yedamFinal.aco.point.PointDetailJoinVO;
import com.yedamFinal.aco.questionboard.MyquestionVO;

public interface MemberMapper {
	// 회원 정보 단건조회
	public MemberVO selectMemberInfo(MemberVO memberVO);
	// 이모티콘 구매내역
	public List<MyemoticonVO> selectMyemoticonList(MemberVO memberVO);
	// 책갈피 목록
	public List<MybookmarkVO> selectMybmList(MemberVO memberVO);
	// 책갈피 모달 목록
	public List<MybookmarkVO> selectMyBookList(MemberVO memberVO);
	//작성한 질문글 리스트
	public List<MyquestionVO> selectMyqList(MemberVO memberVO);
	//작성한 질문글 모달 리스트
	public List<MyquestionVO> selectQuestionList(MemberVO memberVO);
	//사이드 프로젝트 모달 리스트 --> 옮겨야함
	//public List<SideVo> selectSideProjectList(MemberVO memberVO);
	//포인트 사용내역
	public List<PointDetailJoinVO> selectPointDetailList(MemberVO memberVO);
	//활동점수 내역
	public List<ActivityPointVO> selectActivityList(MemberVO memberVO);
	
	public int insertPointDetail(PointDetailJoinVO pointVO);
	public int insertActivityDetail(ActivityPointVO activityVO);
	public int updateMemberPoint(@Param(value="resPoint") int resPoint, @Param(value="m") MemberVO memberVO);
	public int delBookmark(@Param(value="qno") int qnaboardNo,@Param(value="mno") int memberNo);
	public MemberQuestionChartVO selectMemberChart(MemberVO memberVO);
	public List<AccountVO> selectMemberAccountList(MemberVO memberVO);
	
	public MemberVO selectCheckDuplicateId(String id);
	public MemberVO selectCheckDuplicateEmail(String email);
	public MemberVO selectLogin(String id);
	public int insertAuthNumber(@Param(value = "authNum") String authNum, @Param(value = "phoneNum") String phoneNum);
	public String selectAuthNumber(String phoneNum);
	public String selectVerifyAuthNumber(@Param(value = "authNum") String authNum, @Param(value = "phoneNum") String phoneNum);
	public int deleteAuthNumber(String phoneNum);
	public int insertMember(MemberVO memberVO);
	public int updateMemberGitToken(@Param(value ="gitToken") String gitToken, @Param(value = "id") String id);
	public int updateMemberPassword(@Param(value="id") String id, @Param(value="password") String password);
	
	public int insertFindAccountEmailLink(FindAccountEmailLinkVO vo);
	public FindAccountEmailLinkVO selectFindAccountEmailInfo(String accessKey);
	public int deleteFindAccountEmailLink(String accessKey);
	
	public List<TagVO> selectTagList();
	
	public MemberVO selectDuplicateNickname(String nickname);
}
