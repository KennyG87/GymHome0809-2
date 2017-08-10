package com.example.kennykao;

import CoachesLogin.CoachesVO;
import StudentsLogin.StudentsVO;

/**
 * Created by cuser on 2017/8/9.
 */

public class MemberCoach {
    StudentsVO studentsVO;
    CoachesVO coachesVO;
    public MemberCoach(){}

    public StudentsVO getStudentsVO() {
        return studentsVO;
    }

    public void setStudentsVO(StudentsVO studentsVO) {
        this.studentsVO = studentsVO;
    }

    public CoachesVO getCoachesVO() {
        return coachesVO;
    }

    public void setCoachesVO(CoachesVO coachesVO) {
        this.coachesVO = coachesVO;
    }

    public MemberCoach(StudentsVO studentsVO, CoachesVO coachesVO) {
        this.studentsVO = studentsVO;
        this.coachesVO = coachesVO;
    }
}
