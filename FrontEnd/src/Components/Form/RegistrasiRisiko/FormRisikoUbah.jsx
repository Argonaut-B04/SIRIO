import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class FormRisikoUbah extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            nama: "",
            kategori: "",
            sop: "",
            komponen: "",
            redirect: false
        };

        this.renderRoleOption = this.renderRoleOption.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderDataEmployee = this.renderDataEmployee.bind(this);
    }

    componentDidMount() {
        this.renderRoleOption();
        this.renderDataEmployee();
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
        console.log("test");
        EmployeeService.editEmployee(employee)
            .then(() => this.setRedirect());
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
                placeholder: "Nama"
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
                placeholder: "email@email.com"
            }, {
                label: "Nomor Telepon",
                handleChange: this.handleChange,
                type: "text",
                name: "noHp",
                value: this.state.noHp,
                placeholder: "08123456789"
            }]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                             classes="mx-1"
                             onClick={(event)  => this.handleSubmit(event)}>
                    Simpan
                </SirioButton>
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