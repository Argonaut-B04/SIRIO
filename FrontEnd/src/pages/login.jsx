import React from "react";
import SideNav from "../Components/SideNav/SideNav";
import LoginForm from "../Components/Form/LoginForm";
import LogoTag from "../Components/LogoTag/LogoTag";

export default class Login extends React.Component {
    render() {
        return (
            <div className="d-flex w-100">
                <SideNav loginMode={true} classes="d-md-block d-none" />
                <div className="flex-grow-1 p-5">
                    <div className="p-5">
                        <LogoTag dark />
                        <LoginForm />
                    </div>
                </div>
            </div>
        )
    }
}