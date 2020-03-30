import React from "react";
import AuthenticationService from "../../Services/AuthenticationService";
import QAOfficerSideNav from "./RoleSideNav/QAOfficerSideNav";
import LoginSideNav from "./LoginSideNav";
import ManagerOperationalSideNav from "./RoleSideNav/ManagerOperationalSideNav";
import AdministratorSideNav from "./RoleSideNav/AdministratorSideNav";
import BranchManagerSideNav from "./RoleSideNav/BranchManagerSideNav";
import QALeadSideNav from "./RoleSideNav/QALeadSideNav";
import SupervisorSideNav from "./RoleSideNav/SupervisorSideNav";

/**
 * Komponen _Switcher_ yang akan menampilkan SideNav berdasarkan Role
 */
export default class SirioSideNav extends React.Component {

    constructor(props) {
        super(props);

        // Mengambil informasi role dari AuthenticationService
        this.state = {
            role: AuthenticationService.getRole()
        }
    }

    // Render SideNav sesuai role
    render() {
        if (this.props.loginMode) {
            return <LoginSideNav />
        }
        switch (this.state.role) {
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
                return <ManagerOperationalSideNav {...this.props} />
        }
    }
}