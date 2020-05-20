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
            initialEmail:"",
            noHp: "",
            roleOptionList: [],
            redirect: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.renderData = this.renderData.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    componentDidMount() {
        this.renderData();
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
        return this.state.errorName === "" &&
            this.state.errorJabatan === "" &&
            this.state.errorEmail === "" &&
            this.state.errorNoHp === "" &&
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
                    editSuccess: true
                }
            }} />
        }
    };

    async renderData() {
        // Mengubah isi dari loader
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data role dari server");

        const responseRole = await RoleService.getRoleList();
        const roleOptionList = responseRole.data.result.map(role => {
            return (
                {
                    label: role.namaRole,
                    value: role.idRole
                }
            )
        });

        this.props.changeLoadingBody("Mengambil data employee dari server");
        const responseEmployee = await EmployeeService.getEmployee(this.props.location.state.id);

        this.setState({
            roleOptionList: roleOptionList,
            id: responseEmployee.data.result.idEmployee,
            idRole: responseEmployee.data.result.role.idRole,
            nama: responseEmployee.data.result.nama,
            jabatan: responseEmployee.data.result.jabatan,
            email: responseEmployee.data.result.email,
            initialEmail: responseEmployee.data.result.email,
            noHp: this.nomorHpFormatter(responseEmployee.data.result.noHp),
            errorJabatan: "",
            errorName: "",
            errorEmail: "",
            errorNoHp: "",
        }, this.props.contentFinishLoading());
    }

    nomorHpFormatter(noHp) {
        if (noHp) {
            return noHp
        } else {
            return ""
        }
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
            const employee = {
                id: this.state.id,
                idRole: this.state.idRole,
                nama: this.state.nama,
                jabatan: this.state.jabatan,
                email: this.state.email,
                noHp: this.state.noHp
            };

            this.props.contentStartLoading();
            if (this.state.initialEmail !== this.state.email) {
                // Mengubah isi dari loader
                this.props.changeLoadingBody("Mengecek email di server");
                EmployeeService.checkEmailExist({email: this.state.email})
                    .then(response => {
                        if (response.data.result) {
                            this.setState({
                                errorEmail: "Email sudah terdaftar"
                            });
                            this.props.contentFinishLoading();
                        } else {
                            this.props.changeLoadingBody("Mengirim data ke server");
                            EmployeeService.editEmployee(employee)
                                .then(() => this.props.contentFinishLoading(), () => this.props.contentFinishLoading())
                                .then(() => this.setRedirect());
                        }
                    }, () => this.props.contentFinishLoading())
            } else {
                this.props.changeLoadingBody("Mengirim data ke server");
                EmployeeService.editEmployee(employee)
                    .then(() => this.props.contentFinishLoading(), () => this.props.contentFinishLoading())
                    .then(() => this.setRedirect());
            }
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
                    label: "Role",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    required: true,
                    name: "idRole",
                    value: this.state.idRole,
                    optionList: this.state.roleOptionList
                }, {
                    label: "Nama",
                    handleChange: this.handleChange,
                    type: "text",
                    required: true,
                    name: "nama",
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
                <NavLink to={{
                    pathname: "/employee/detail",
                    state: {
                        id: this.props.location.state.id
                    }
                }}>
                    <SirioButton
                        purple
                        classes="mx-1"
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