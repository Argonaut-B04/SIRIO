import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import EmployeeService from '../../../Services/EmployeeService';
import RoleService from '../../../Services/RoleService';
import { Redirect } from 'react-router-dom';

export default class EmployeeFormTambah extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",
            idRole: "",
            nama: "",
            jabatan: "",
            email: "",
            noHp: "",
            roleOptionList: [],
            redirect: false
        };

        this.renderRoleOption = this.renderRoleOption.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    componentDidMount() {
        this.renderRoleOption();
    }

    validateJabatan(fokusJabatan) {
        var errorJabatan = "";
        if (fokusJabatan === null || fokusJabatan === "") {
            errorJabatan = "";
        } else if (fokusJabatan.length > 30) {
            errorJabatan = "Jabatan maksimal 30 karakter";
        }

        this.setState({
            errorJabatan: errorJabatan
        })

    }

    validateNama(fokusName) {
        var errorName = "";
        var letterOnly = /^[a-zA-Z\s]*$/;
        if (fokusName === null || fokusName === "") {
            errorName = "";
        } else if (!fokusName.match(letterOnly)) {
            errorName = "Nama hanya boleh mengandung huruf";
        } else if (fokusName.length > 50) {
            errorName = "Nama maksimal 50 karakter";
        }

        this.setState({
            errorName: errorName
        })
    }

    validateNomorHp(fokusNoHp) {
        var errorNoHp = "";
        var numberOnly = /^[0-9]*$/;

        if (fokusNoHp === null || fokusNoHp === "") {
            errorNoHp = "";
        } else if (!fokusNoHp.match(numberOnly)) {
            errorNoHp = "Nomor HP hanya boleh mengandung angka";
        } else if (fokusNoHp.length >= 15 || fokusNoHp.length <= 10) {
            errorNoHp = "Nomor HP diantara 10-15 karakter";
        }

        this.setState({
            errorNoHp: errorNoHp
        })
    }

    validatePassword(fokusPassword) {
        var errorPassword = "";
        var letterNumber = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{0,}$/;
        if (fokusPassword === null || fokusPassword === "") {
            errorPassword = "";
        } else if (!fokusPassword.match(letterNumber)) {
            errorPassword = "Password harus mengandung angka, 1 uppercase, dan 1 lowercase";
        } else if (fokusPassword.length < 8) {
            errorPassword = "Password minimal 8 karakter";
        } else if (fokusPassword.length > 70) {
            errorPassword = "Password maksimal 70 karakter";
        }

        this.setState({
            errorPassword: errorPassword
        })
    }

    validateUsername(fokusUsername) {
        var errorUsername = "";
        var letterNumber = /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/;
        if (fokusUsername === null || fokusUsername === "") {
            errorUsername = "";
        } else if (!fokusUsername.match(letterNumber)) {
            errorUsername = "Username hanya dan harus mengandung angka dan huruf";
        } else if (fokusUsername.length > 20) {
            errorUsername = "Username maksimal 20 karakter";
        }

        this.setState({
            errorUsername: errorUsername
        })
    }

    validateEmail(fokusEmail) {
        var errorEmail = "";
        // eslint-disable-next-line
        var email = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (fokusEmail === null || fokusEmail === "") {
            errorEmail = "";
        } else if (!fokusEmail.match(email)) {
            errorEmail = "Email tidak sesuai format";
        }

        this.setState({
            errorEmail: errorEmail
        })
    }

    submitable() {
        return this.state.errorUsername === "" &&
            this.state.errorPassword === "" &&
            this.state.errorName === "" &&
            this.state.errorJabatan === "" &&
            this.state.errorEmail === "" &&
            this.state.errorNoHp === "" &&
            (this.state.username !== null && this.state.username !== "") &&
            (this.state.password !== null && this.state.password !== "") &&
            (this.state.idRole !== null && this.state.idRole !== "") &&
            (this.state.nama !== null && this.state.nama !== "") &&
            (this.state.jabatan !== null && this.state.jabatan !== "") &&
            (this.state.email !== null && this.state.email !== "");
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/employee",
                state: {
                    addSuccess: true
                }
            }} />
        }
    };

    async renderRoleOption() {
        // Mengubah isi dari loader
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const response = await RoleService.getRoleList();

        // Mengubah isi dari loader
        this.props.changeLoadingBody("Menampilkan data");

        const roleOptionList = response.data.result.map(role => {
            return (
                {
                    label: role.namaRole,
                    value: role.idRole
                }
            )
        });

        this.setState({
            roleOptionList: roleOptionList
        }, this.props.contentFinishLoading())
    }

    handleChange(event) {
        const { name, value } = event.target;
        this.setState(
            {
                [name]
                    : value
            }
        );

        switch (name) {
            case "username":
                this.validateUsername(value);
                break;
            case "password":
                this.validatePassword(value);
                break;
            case "nama":
                this.validateNama(value);
                break;
            case "jabatan":
                this.validateJabatan(value);
                break;
            case "email":
                this.validateEmail(value);
                break;
            case "noHp":
                this.validateNomorHp(value);
                break;
        }
    }

    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        );
    }

    async handleSubmit(event) {
        event.preventDefault();
        if (this.submitable()) {
            this.props.contentStartLoading();
            this.props.changeLoadingBody("Mengecek username di server");

            EmployeeService.checkEmployeeExist(this.state.username)
                .then(response => {
                    if (response.data.result) {
                        this.setState({
                            errorUsername: "Username sudah terdaftar"
                        }, this.props.contentFinishLoading())
                    } else {
                        this.props.changeLoadingBody("Mengecek email di server");
                        EmployeeService.checkEmailExist({ email: this.state.email })
                            .then(response => {
                                if (response.data.result) {
                                    this.setState({
                                        errorEmail: "Email sudah terdaftar"
                                    }, this.props.contentFinishLoading())
                                } else {
                                    const employee = {
                                        username: this.state.username,
                                        password: this.state.password,
                                        idRole: this.state.idRole,
                                        nama: this.state.nama,
                                        jabatan: this.state.jabatan,
                                        email: this.state.email,
                                        noHp: this.state.noHp
                                    };
                                    this.props.changeLoadingBody("Mengirim data ke server");
                                    EmployeeService.addEmployee(employee)
                                        .then(() => this.props.contentFinishLoading(), () => this.props.contentFinishLoading())
                                        .then(() => this.setRedirect());
                                }
                            })
                    }
                }, () => this.props.contentFinishLoading());
        }
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Username",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "username",
                    required: true,
                    value: this.state.username,
                    placeholder: "Username",
                    errormessage: this.state.errorUsername
                }, {
                    label: "Password",
                    handleChange: this.handleChange,
                    type: "password",
                    name: "password",
                    required: true,
                    value: this.state.password,
                    placeholder: "Password",
                    errormessage: this.state.errorPassword
                }, {
                    label: "Role",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "idRole",
                    required: true,
                    value: this.state.idRole,
                    optionList: this.state.roleOptionList
                }, {
                    label: "Nama",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "nama",
                    required: true,
                    value: this.state.nama,
                    placeholder: "Nama",
                    errormessage: this.state.errorName
                }, {
                    label: "Jabatan",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "jabatan",
                    required: true,
                    value: this.state.jabatan,
                    placeholder: "Jabatan",
                    errormessage: this.state.errorJabatan
                }, {
                    label: "Email",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "email",
                    required: true,
                    value: this.state.email,
                    placeholder: "email@email.com",
                    errormessage: this.state.errorEmail
                }, {
                    label: "Nomor Telepon",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "noHp",
                    value: this.state.noHp,
                    placeholder: "08123456789",
                    errormessage: this.state.errorNoHp
                }]
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
                    onClick={(event) => this.handleSubmit(event)}
                >
                    Simpan
                </SirioButton>
        }
        return (
            <div>
                {tombolSimpan}
                <SirioButton purple
                    classes="mx-1"
                    onClick={() => window.location.href = "/employee"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Tambah Pengguna"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}