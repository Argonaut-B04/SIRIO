import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import EmployeeService from '../../../Services/EmployeeService';
import { withRouter } from 'react-router-dom';
import SirioMessageButton from "../../Button/ActionButton/SirioMessageButton";

class EmployeeFormUbahPassword extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            password: "",
            newPassword: "",
            // redirect: false,
            errorNewPassword: "",
            changeSuccess: false,
            changeFail: false,
        };

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        // this.setRedirect = this.setRedirect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.submitable = this.submitable.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.props.contentFinishLoading();
    }

    validateNewPassword(fokusNewPassword) {
        var errorNewPassword = "";
        var letterNumber = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{0,}$/;
        if (fokusNewPassword === null || fokusNewPassword === "") {
            errorNewPassword = "";
        } else if (!fokusNewPassword.match(letterNumber)) {
            errorNewPassword = "Password harus mengandung angka, 1 uppercase, dan 1 lowercase";
        } else if (fokusNewPassword.length < 8) {
            errorNewPassword = "Password minimal 8 karakter";
        } else if (fokusNewPassword.length > 70) {
            errorNewPassword = "Password maksimal 70 karakter";
        }

        this.setState({
            errorNewPassword: errorNewPassword
        })
    }

    submitable() {
        return this.state.errorNewPassword === "" &&
            (this.state.password !== null && this.state.password !== "") &&
            (this.state.newPassword !== null && this.state.newPassword !== "");
    }

    // setRedirect = () => {
    //     this.setState({
    //         redirect: true
    //     })
    // };
    //
    // renderRedirect = () => {
    //     if (this.state.redirect) {
    //         return <Redirect to={{
    //             pathname: "/employee",
    //             state: {
    //                 editSuccess: true
    //             }
    //         }} />
    //     }
    // };

    handleChange(event) {
        const { name, value } = event.target;
        this.setState(
            {
                [name]
                    : value
            }
        );

        if (name === "newPassword") this.validateNewPassword(value);
    }

    async handleSubmit(event) {
        event.preventDefault();
        if (this.submitable()) {
            const employee = {
                password: this.state.password,
                newPassword: this.state.newPassword
            };

            this.props.contentStartLoading();
            this.props.changeLoadingBody("Mengirim data ke server");

            EmployeeService.changePassword(employee)
                .then(response => {
                    if (response.data.result) {
                        this.setState({
                            changeSuccess: true
                        });
                    } else {
                        console.log(this.state.changeFail)
                        this.setState({
                            changeFail: true
                        });
                        console.log(this.state.changeFail)
                    }
                });
            this.props.contentFinishLoading()
        }
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Old Password",
                    handleChange: this.handleChange,
                    type: "password",
                    name: "password",
                    required: true,
                    value: this.state.password,
                    placeholder: "Old Password"
                }, {
                    label: "New Password",
                    handleChange: this.handleChange,
                    type: "password",
                    required: true,
                    name: "newPassword",
                    value: this.state.newPassword,
                    placeholder: "New Password",
                    errormessage: this.state.errorNewPassword
                }
            ]
        )
    }

    submitButton() {
        var tombolSimpan =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Simpan
            </SirioButton>;
        if (this.submitable()) {
            tombolSimpan =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event)  => this.handleSubmit(event)}
                >
                    Simpan
                </SirioButton>
        }
        return (
            <div>
                {tombolSimpan}
                {/*<NavLink to={{*/}
                {/*    pathname: "/employee/detail",*/}
                {/*    state: {*/}
                {/*        id: this.props.location.state.id*/}
                {/*    }*/}
                {/*}}>*/}
                {/*    <SirioButton*/}
                {/*        purple*/}
                {/*        classes="mx-1"*/}
                {/*    >*/}
                {/*        Batal*/}
                {/*    </SirioButton>*/}
                {/*</NavLink>*/}
            </div>
        )
    }

    endNotification() {
        this.setState({
            changeSuccess: false,
            changeFail: false
        })
    }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                {/*{this.renderRedirect()}*/}
                <SirioForm
                    title="Form Ubah Password"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
                {this.state.changeSuccess &&
                    <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Password berhasil diubah"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                    />
                }
                {this.state.changeFail &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Password lama tidak tepat"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
            </>
        );
    }
}

export default withRouter(EmployeeFormUbahPassword);