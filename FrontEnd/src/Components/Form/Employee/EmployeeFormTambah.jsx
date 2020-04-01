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
            roleOptionList: []
        }

        this.renderRoleOption = this.renderRoleOption.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
    }

    componentDidMount() {
        this.renderRoleOption();
    }

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
    
    handleSubmit(event) {
        const employee = {
            username: this.state.username,
            password: this.state.password,
            idRole: this.state.idRole,
            nama: this.state.nama,
            jabatan: this.state.jabatan,
            email: this.state.email,
            noHp: this.state.noHp
        }
        EmployeeService.submitChanges(employee)
            .then(() => {
                return (
                    <Redirect to={{
                        pathname: "/employee"
                    }}>
                    </Redirect>
                )
            });
        event.preventDefault();
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
                    placeholder: "Username"
                }, {
                    label: "Password",
                    handleChange: this.handleChange,
                    type: "password",
                    name: "password",
                    value: this.state.password,
                    placeholder: "Password"
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
                             onClick={() => this.handleSubmit()}>
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
            <SirioForm
                title="Form Tambah Pengguna"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
                submitButton={this.submitButton()}
            />
        );
    }
}