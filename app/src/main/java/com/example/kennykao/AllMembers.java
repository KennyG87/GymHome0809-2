package com.example.kennykao;

import CoachesLogin.CoachesVO;
import StudentsLogin.StudentsVO;

/**
 * Created by cuser on 2017/8/9.
 */

public class AllMembers {
    StudentsVO studentsVO;
    CoachesVO coachesVO;
    MembersVO membersVO;

    public AllMembers(){}

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

    public MembersVO getMembersVO(){ return membersVO;}

    public void setMembersVO(MembersVO membersVO) { this.membersVO = membersVO;}

    public AllMembers(StudentsVO studentsVO, CoachesVO coachesVO) {
        this.studentsVO = studentsVO;
        this.coachesVO = coachesVO;
        this.membersVO = membersVO;
    }
}
