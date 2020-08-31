package com.gome.controller;

import com.gome.constant.GomeConstant;
import com.gome.pojo.FinalScore;
import com.gome.pojo.GomeUser;
import com.gome.pojo.JudgesScores;
import com.gome.pojo.QaScoresRecord;
import com.gome.service.FinalScoreService;
import com.gome.service.GomeUserService;
import com.gome.service.JudgesScoresService;
import com.gome.service.QaScoresRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.gome.constant.GomeConstant.*;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/18 13:28
 * @Modified By:
 */
@Controller
public class ScoreController {

    @Autowired
    private GomeUserService gomeUserService;
    @Autowired
    private QaScoresRecordService qaScoresRecordService;
    @Autowired
    private FinalScoreService finalScoreService;
    @Autowired
    private JudgesScoresService judgesScoresService;

    /**
     * 计算得分，并将
     * @return
     */

    @GetMapping("/score")
    public String to_Wait(@RequestParam String thisLinks, int userSortnum, HttpServletRequest request ,Model model){

        HttpSession session = request.getSession();
        GomeUser gomeUser = (GomeUser) session.getAttribute(GomeConstant.USER);
        //第二环节
        if(thisLinks.equals("2")){
            //如果是当前用户
            if(gomeUser.getCompetitionOrder().equals(userSortnum)){
                //查询第一环节，当前用户的得分
                Double scores =0.0;
                List<JudgesScores> judgesScoresList = judgesScoresService.findAllByPlayerId(gomeUser.getUserId(),"1");
                //查询第二环节，当前用户的得分
                List<QaScoresRecord>qaScoresRecordList = qaScoresRecordService.findAllByUser(gomeUser.getUserName());

                int count=1;//累加评委人数
                String judges_name="";
                //遍历第一环节和第二环节的得分，
                if(judgesScoresList.size()>0){
                    judges_name = judgesScoresList.get(0).getJudgesName();
                }
                for (JudgesScores judgesScores :judgesScoresList){
                    scores+=(Double) judgesScores.getScore();
                    if(!judges_name.equals(judgesScores.getJudgesName())){
                        count++;
                    }
                    System.out.println(judgesScores.toString());
                }
                scores/=count;
                FinalScore finalScore = new FinalScore();
                finalScore.setThisLinks("1");
                finalScore.setUserName(gomeUser.getUserName());
                finalScore.setCompetitionOrder(gomeUser.getCompetitionOrder().toString());
                finalScore.setUserPersonsName(gomeUser.getUserPersonsName());
                finalScore.setFinalScore(scores);
                if(finalScoreService.saveScore(finalScore)){
                    System.out.println("添加成功");
                }


                //存入第二环节
                for (QaScoresRecord qaScoresRecord : qaScoresRecordList){
                    scores=(Double) qaScoresRecord.getScore();
                }
                finalScore.setThisLinks("2");
                finalScore.setFinalScore(scores);
                if(finalScoreService.saveScore(finalScore)){
                    System.out.println("添加成功");
                }


            }

            //查询成绩
            List<FinalScore> finalScores = finalScoreService.findByAll();
            model.addAttribute("finalScores",finalScores);

        }
        return "score";
    }


}
