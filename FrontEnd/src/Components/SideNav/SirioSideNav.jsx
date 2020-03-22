import React from "react";
import AuthenticationService from "../../Services/AuthenticationService";
import QAOfficerSideNav from "./RoleSideNav/QAOfficerSideNav";
import LoginSideNav from "./LoginSideNav";

class SirioSideNav extends React.Component {

    render() {
        if (this.props.loginMode) {
            return <LoginSideNav />
        }

        let role = AuthenticationService.getRole();
        switch (role) {
            case "admin":
                return <QAOfficerSideNav {...this.props} />
            default:
                return <QAOfficerSideNav {...this.props} />
        }

    }
}
export default SirioSideNav;