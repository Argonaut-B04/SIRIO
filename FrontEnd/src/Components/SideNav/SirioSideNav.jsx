import React from "react";
import AuthenticationService from "../../Services/AuthenticationService";
import QAOfficerSideNav from "./RoleSideNav/QAOfficerSideNav";
import LoginSideNav from "./LoginSideNav";
import ManagerOperationalSideNav from "./RoleSideNav/ManagerOperationalSideNav";
import AdministratorSideNav from "./RoleSideNav/AdministratorSideNav";
import BranchManagerSideNav from "./RoleSideNav/BranchManagerSideNav";
import QALeadSideNav from "./RoleSideNav/QALeadSideNav";
import SupervisorSideNav from "./RoleSideNav/SupervisorSideNav";

class SirioSideNav extends React.Component {

    render() {
        if (this.props.loginMode) {
            return <LoginSideNav />
        }

        let role = AuthenticationService.getRole();
        switch (role) {
            case "Manajer Operational Risk":
                return <ManagerOperationalSideNav {...this.props} />
            case "Administrator":
                return <AdministratorSideNav {...this.props} />
            case "QA Officer Operational Risk":
                return <QAOfficerSideNav {...this.props} />
            case "Branch Manager":
                return <BranchManagerSideNav {...this.props} />
            case "QA Lead Operational Risk":
                return <QALeadSideNav {...this.props} />
            case "Supervisor":
                return <SupervisorSideNav {...this.props} />
            default:
                return <QAOfficerSideNav {...this.props} />
        }
    }
}
export default SirioSideNav;