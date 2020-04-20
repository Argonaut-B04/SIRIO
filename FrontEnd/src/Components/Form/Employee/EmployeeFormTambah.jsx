import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import EmployeeService from '../../../Services/EmployeeService';
import RoleService from '../../../Services/RoleService';
import { Redirect } from 'react-router-dom';
import HasilPemeriksaanService from "../../../Services/HasilPemeriksaanService";

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
            redirect: false,
            submitable: false,
        };

        this.renderRoleOption = this.renderRoleOption.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.renderRoleOption();
    }

    componentDidUpdate(prevProps, prevState) {
        var submitable = true;
        var validating = false;

        submitable = this.validateRequired();

        if (prevState.nama !== this.state.nama) {
            const validation = this.validateNama();
            submitable = submitable && validation;
            validating = true;
        }

        if (prevState.email !== this.state.email) {
            const validation = this.validateEmail();
            submitable = submitable && validation;
            validating = true;
        }

        if (prevState.password !== this.state.password) {
            const validation = this.validatePassword();
            submitable = submitable && validation;
            validating = true;
        }

        if (prevState.noHp !== this.state.noHp) {
            const validation = this.validateNomorHp();
            submitable = submitable && validation;
            validating = true;
        }

        if (prevState.username !== this.state.username) {
            const validation = this.validateUsername();
            submitable = submitable && validation;
            validating = true;
        }

        if (prevState.jabatan !== this.state.jabatan) {
            const validation = this.validateJabatan();
            submitable = submitable && validation;
            validating = true;
        }

        if (prevState.idRole !== this.state.idRole) {
            validating = true;
        }

        if (validating) {
            if (this.state.submitable !== submitable) {
                this.setState({
                    submitable: submitable
                })
            }
        }
    }

    validateRequired() {
        var submitable = true;
        const required = [
            this.state.username,
            this.state.password,
            this.state.idRole,
            this.state.nama,
            this.state.jabatan,
            this.state.email
        ];

        for (let i = 0; i < required.length; i++) {
            submitable = submitable && (required[i] !== null && required[i] !== "");
        }
        return submitable;
    }

    validateJabatan() {
        var submitable = true;
        const fokusJabatan = this.state.jabatan;
        var errorJabatan;
        if (fokusJabatan.length > 30) {
            submitable = false;
            errorJabatan = "Jabatan maksimal 30 karakter";
        }
        if (this.state.errorJabatan !== errorJabatan) {
            this.setState({
                errorJabatan: errorJabatan
            })
        }
        return submitable;
    }

    validateNama() {
        var submitable = true;
        const fokusName = this.state.nama;
        var errorName;
        var letterOnly = /^[a-zA-Z\s]*$/;
        if (!fokusName.match(letterOnly)) {
            submitable = false;
            errorName = "Nama hanya boleh mengandung huruf";
        } else if (fokusName.length > 50) {
            submitable = false;
            errorName = "Nama maksimal 50 karakter";
        }
        if (this.state.errorName !== errorName) {
            this.setState({
                errorName: errorName
            })
        }
        return submitable;
    }

    validateNomorHp() {
        var submitable = true;
        const fokusNoHp = this.state.noHp;
        var errorNoHp;
        var numberOnly = /^[0-9]*$/;
        if (!fokusNoHp.match(numberOnly)) {
            submitable = false;
            errorNoHp = "Nomor HP hanya boleh mengandung angka";
        } else if (fokusNoHp.length > 20) {
            submitable = false;
            errorNoHp = "Nomor HP maksimal 20 karakter";
        }
        if (this.state.errorNoHp !== errorNoHp) {
            this.setState({
                errorNoHp: errorNoHp
            })
        }
        return submitable;
    }

    validatePassword() {
        var submitable = true;
        const fokusPassword = this.state.password;
        var errorPassword;
        var letterNumber = /[^\w\d]*(([0-9]+.*[A-Za-z]+.*)|[A-Za-z]+.*([0-9]+.*))/;
        if (!fokusPassword.match(letterNumber)) {
            submitable = false;
            errorPassword = "Password harus mengandung angka dan huruf";
        } else if (fokusPassword.length < 8) {
            submitable = false;
            errorPassword = "Password minimal 8 karakter";
        } else if (fokusPassword.length > 70) {
            submitable = false;
            errorPassword = "Password maksimal 70 karakter";
        }
        if (this.state.errorPassword !== errorPassword) {
            this.setState({
                errorPassword: errorPassword
            })
        }
        return submitable;
    }

    validateUsername() {
        var submitable = true;
        const fokusUsername = this.state.username;
        var errorUsername;
        var letterNumber = /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/;
        if (!fokusUsername.match(letterNumber)) {
            submitable = false;
            errorUsername = "Username mengandung angka dan huruf";
        } else if (fokusUsername.length > 20) {
            submitable = false;
            errorUsername = "Username maksimal 20 karakter";
        }
        if (this.state.errorUsername !== errorUsername) {
            this.setState({
                errorUsername: errorUsername
            })
        }
        return submitable;
    }

    validateEmail() {
        var submitable = true;
        const fokusEmail = this.state.email;
        var errorEmail;
        var email =
            /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!fokusEmail.match(email)) {
            submitable = false;
            errorEmail = "Email tidak sesuai format";
        }
        if (this.state.errorEmail !== errorEmail) {
            this.setState({
                errorEmail: errorEmail
            })
        }
        return submitable;
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
        const response = await RoleService.getRoleList();

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
        })
    }
    
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }
    
    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }
    
    async handleSubmit(event) {
        event.preventDefault();
        if (this.state.submitable) {
            const response = await EmployeeService.checkEmployeeExist(this.state.username);
            if (response.data.result) {
                const errorUsername = "Username sudah terdaftar";
                if (this.state.errorUsername !== errorUsername) {
                    this.setState({
                        errorUsername: errorUsername
                    })
                }
            } else {
                console.log("yet")
                const employee = {
                    username: this.state.username,
                    password: this.state.password,
                    idRole: this.state.idRole,
                    nama: this.state.nama,
                    jabatan: this.state.jabatan,
                    email: this.state.email,
                    noHp: this.state.noHp
                };
                EmployeeService.addEmployee(employee)
                    .then(() => this.setRedirect());
            }
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
                    value: this.state.username,
                    placeholder: "Username",
                    validation: this.state.errorUsername
                }, {
                    label: "Password",
                    handleChange: this.handleChange,
                    type: "password",
                    name: "password",
                    value: this.state.password,
                    placeholder: "Password",
                    validation: this.state.errorPassword
                }, {
                    label: "Role",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "idRole",
                    value: this.state.idRole,
                    optionList: this.state.roleOptionList
                }, {
                    label: "Nama",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "Nama",
                    validation: this.state.errorName
                }, {
                    label: "Jabatan",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "jabatan",
                    value: this.state.jabatan,
                    placeholder: "Jabatan",
                    validation: this.state.errorJabatan
                }, {
                    label: "Email",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "email",
                    value: this.state.email,
                    placeholder: "email@email.com",
                    validation: this.state.errorEmail
                }, {
                    label: "Nomor Telepon",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "noHp",
                    value: this.state.noHp,
                    placeholder: "08123456789",
                    validation: this.state.errorNoHp
                }]
        )
    }

    handleReset(event) {
        event.preventDefault();
        this.setState({
            username: "",
            password: "",
            idRole: "",
            nama: "",
            jabatan: "",
            email: "",
            noHp: "",
            submitable: false,
        })
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple
                             recommended={this.state.submitable}
                             disabled={!this.state.submitable}
                             classes="mx-1"
                             onClick={(event)  => this.handleSubmit(event)}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                             classes="mx-1"
                             onClick={() => window.location.href = "/employee"}>
                    Batal
                </SirioButton>
                <SirioButton
                    purple
                    classess="mx-1"
                    onClick={(event)  => this.handleReset(event)}>
                    Reset
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