import React from "react";
import SirioSideNav from "../Components/SideNav/SirioSideNav";
import LoginForm from "../Components/Form/LoginForm";
import LogoTag from "../Components/LogoTag/LogoTag";

/**
 * Controller untuk menampilkan halaman login
 */
export default class Login extends React.Component {
    render() {
        return (
            <div className="d-flex w-100">
                <SirioSideNav loginMode={true} classes="d-md-block d-none" />
                <div className="flex-grow-1 p-5">
                    <div className="p-5">
                        <LogoTag dark />
                        <LoginForm history={this.props.history} />
                    </div>
                </div>
            </div>
        )
    }
}