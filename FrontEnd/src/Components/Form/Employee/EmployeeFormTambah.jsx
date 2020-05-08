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
            redirect: false,
            submitable: [false,false,false,false,false,false,false]
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
        var submitableArray = this.state.submitable;
        var submitable = true;
        var errorJabatan;
        if (fokusJabatan === null || fokusJabatan === "") {
            submitable = false;
        } else if (fokusJabatan.length > 30) {
            submitable = false;
            errorJabatan = "Jabatan maksimal 30 karakter";
        }

        submitableArray[4] = submitable;
        this.setState({
            errorJabatan: errorJabatan,
            submitable: submitableArray
        })

    }

    validateNama(fokusName) {
        var submitableArray = this.state.submitable;
        var submitable = true;
        var errorName;
        var letterOnly = /^[a-zA-Z\s]*$/;
        if (fokusName === null || fokusName === "") {
            submitable = false;
        } else if (!fokusName.match(letterOnly)) {
            submitable = false;
            errorName = "Nama hanya boleh mengandung huruf";
        } else if (fokusName.length > 50) {
            submitable = false;
            errorName = "Nama maksimal 50 karakter";
        }

        submitableArray[3] = submitable;
        this.setState({
            errorName: errorName,
            submitable: submitableArray
        })
    }

    validateNomorHp(fokusNoHp) {
        var submitableArray = this.state.submitable;
        var submitable = true;
        var errorNoHp;
        var numberOnly = /^[0-9]*$/;
        if (fokusNoHp === null || fokusNoHp === "") {
            submitable = false;
        } else if (!fokusNoHp.match(numberOnly)) {
            submitable = false;
            errorNoHp = "Nomor HP hanya boleh mengandung angka";
        } else if (fokusNoHp.length > 20) {
            submitable = false;
            errorNoHp = "Nomor HP maksimal 20 karakter";
        }

        submitableArray[6] = submitable;
        this.setState({
            errorNoHp: errorNoHp,
            submitable: submitableArray
        })
    }

    validatePassword(fokusPassword) {
        var submitableArray = this.state.submitable;
        var submitable = true;
        var errorPassword;
        var letterNumber = /[^\w\d]*(([0-9]+.*[A-Za-z]+.*)|[A-Za-z]+.*([0-9]+.*))/;
        if (fokusPassword === null || fokusPassword === "") {
            submitable = false;
        } else if (!fokusPassword.match(letterNumber)) {
            submitable = false;
            errorPassword = "Password harus mengandung angka dan huruf";
        } else if (fokusPassword.length < 8) {
            submitable = false;
            errorPassword = "Password minimal 8 karakter";
        } else if (fokusPassword.length > 70) {
            submitable = false;
            errorPassword = "Password maksimal 70 karakter";
        }

        submitableArray[1] = submitable;
        this.setState({
            errorPassword: errorPassword,
            submitable: submitableArray
        })
    }

    validateUsername(fokusUsername) {
        var submitableArray = this.state.submitable;
        var submitable = true;
        var errorUsername;
        var letterNumber = /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/;
        if (fokusUsername === null || fokusUsername === "") {
            submitable = false;
        } else if (!fokusUsername.match(letterNumber)) {
            submitable = false;
            errorUsername = "Username mengandung angka dan huruf";
        } else if (fokusUsername.length > 20) {
            submitable = false;
            errorUsername = "Username maksimal 20 karakter";
        }

        submitableArray[0] = submitable;
        this.setState({
            errorUsername: errorUsername,
            submitable: submitableArray
        })
    }

    validateEmail(fokusEmail) {
        var submitableArray = this.state.submitable;
        var submitable = true;
        var errorEmail;
        // eslint-disable-next-line
        var email = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (fokusEmail === null || fokusEmail === "") {
            submitable = false;
        } else if (!fokusEmail.match(email)) {
            submitable = false;
            errorEmail = "Email tidak sesuai format";
        }

        submitableArray[5] = submitable;
        this.setState({
            errorEmail: errorEmail,
            submitable: submitableArray
        })
    }

    validateRole(fokusRole) {
        var submitableArray = this.state.submitable;
        var submitable = true;

        if (fokusRole === null || fokusRole === "") {
            submitable = false;
        }

        submitableArray[2] = submitable;
        this.setState({
            submitable: submitableArray
        })
    }

    submitable() {
        var submitable = true;
        for (let i = 0; i < 5; i++) {
            submitable = submitable && this.state.submitable[i];
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

        if (name === "idRole") this.validateRole(event.value);
    }

    async handleSubmit(event) {
        event.preventDefault();
        if (this.submitable()) {
            const response = await EmployeeService.checkEmployeeExist(this.state.username);
            if (response.data.result) {
                const errorUsername = "Username sudah terdaftar";
                if (this.state.errorUsername !== errorUsername) {
                    this.setState({
                        errorUsername: errorUsername
                    })
                }
            } else {
                const response = await EmployeeService.checkEmailExist({email: this.state.email});
                if (response.data.result) {
                    const errorEmail = "Email sudah terdaftar";
                    if (this.state.errorEmail !== errorEmail) {
                        this.setState({
                            errorEmail: errorEmail
                        })
                    }
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
                    EmployeeService.addEmployee(employee)
                        .then(() => this.setRedirect());
                }
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
                    errormessage: this.state.errorUsername
                }, {
                    label: "Password",
                    handleChange: this.handleChange,
                    type: "password",
                    name: "password",
                    value: this.state.password,
                    placeholder: "Password",
                    errormessage: this.state.errorPassword
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
                    errormessage: this.state.errorName
                }, {
                    label: "Jabatan",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "jabatan",
                    value: this.state.jabatan,
                    placeholder: "Jabatan",
                    errormessage: this.state.errorJabatan
                }, {
                    label: "Email",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "email",
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
                    onClick={(event)  => this.handleSubmit(event)}
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