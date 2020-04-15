import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import EmployeeService from '../../../Services/EmployeeService';
import RoleService from '../../../Services/RoleService';
import { NavLink, Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class EmployeeFormUbah extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            id: "",
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
        this.renderDataEmployee = this.renderDataEmployee.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.renderRoleOption();
        this.renderDataEmployee();
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

        if (prevState.noHp !== this.state.noHp) {
            const validation = this.validateNomorHp();
            submitable = submitable && validation;
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

    validateNama() {
        var submitable = true;
        const fokusName = this.state.nama;
        var errorName;
        var letterOnly = /^[a-zA-Z\s]*$/;
        if (!fokusName.match(letterOnly)) {
            console.log("sfa")
            console.log(fokusName)
            submitable = false;
            errorName = "Nama hanya boleh mengandung huruf";
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
        }
        if (this.state.errorNoHp !== errorNoHp) {
            this.setState({
                errorNoHp: errorNoHp
            })
        }
        return submitable;
    }

    validateEmail() {
        var submitable = true;
        const fokusEmail = this.state.email;
        var errorEmail;
        // eslint-disable-next-line
        var email = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
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
                    editSuccess: true
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

    async renderDataEmployee() {
        const response = await EmployeeService.getEmployee(this.props.location.state.id);

        this.setState({
            id: response.data.result.idEmployee,
            idRole: response.data.result.role.idRole,
            nama: response.data.result.nama,
            jabatan: response.data.result.jabatan,
            email: response.data.result.email,
            noHp: this.nomorHpFormatter(response.data.result.noHp),
        })
    }

    nomorHpFormatter(noHp) {
        if (noHp) {
            return noHp
        } else {
            return ""
        }
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

    handleSubmit(event) {
        event.preventDefault();
        const employee = {
            id: this.state.id,
            idRole: this.state.idRole,
            nama: this.state.nama,
            jabatan: this.state.jabatan,
            email: this.state.email,
            noHp: this.state.noHp
        };
        if (this.state.submitable) {
            EmployeeService.editEmployee(employee)
                .then(() => this.setRedirect());
        }
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
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
                    placeholder: "Jabatan"
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

    submitButton() {
        return (
            <div>
                <SirioButton purple
                    recommended={this.state.submitable}
                    disabled={!this.state.submitable}
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event)}>
                    Simpan
                </SirioButton>
                <NavLink to={{
                    pathname: "/employee/detail",
                    state: {
                        id: this.props.location.state.id
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Batal
                    </SirioButton>
                </NavLink>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Ubah Pengguna"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}

export default withRouter(EmployeeFormUbah);