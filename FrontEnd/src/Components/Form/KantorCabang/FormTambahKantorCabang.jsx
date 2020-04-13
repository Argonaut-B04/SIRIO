import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import KantorCabangService from '../../../Services/KantorCabangService';
import EmployeeService from '../../../Services/EmployeeService';
import { Redirect } from 'react-router-dom';

/**
 * Kelas untuk membuat form demo
 */
export default class FormTambahKantorCabang extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            namaKantorCabang: "",
            idPemilik: "",
            area: "",
            regional: "",
            kunjunganAudit: false,
            employeeOptionList: [],
            redirect: false,
            submitable: true,
        }

        this.renderEmployeeOption = this.renderEmployeeOption.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
    }

    componentDidMount() {
        this.renderEmployeeOption();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/administrator/kantorCabang",
                state: {
                    addSuccess: true
                }
            }} />
        }
    };

    async renderEmployeeOption() {
        const response = await EmployeeService.getAllBM();

        const employeeOptionList = response.data.result.map(employee => {
            return (
                {
                    label: employee.nama,
                    value: employee.idEmployee
                }
            )
        });

        this.setState({
            employeeOptionList: employeeOptionList
        })
    }

    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi ini wajib ada jika membuat field tipe select
    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi checkbox diubah
    // Fungsi ini wajib ada jika membuat field tipe checked
    handleInputChange(event) {
        const target = event.target;
        const value = target.name === 'kunjunganAudit' ? target.checked : target.value;
        const name = target.name;
    
        this.setState({
          [name]: value
        });
    }

    componentDidUpdate() {
        var submitable = true;

        submitable = this.validateKantor() && this.validateArea() && this.validateRegional() && this.validateBM();

        if (this.state.submitable !== submitable) {
            this.setState({
                submitable: submitable
            })
        }
    }


    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    handleSubmit(event) {
        event.preventDefault();
        const kantorCabang = {
            namaKantorCabang: this.state.namaKantorCabang,
            idPemilik: this.state.idPemilik,
            area: this.state.area,
            regional: this.state.regional,
            kunjunganAudit: this.state.kunjunganAudit
        }
        KantorCabangService.addKantorCabang(kantorCabang)
        .then(() => this.setRedirect());
    }

    validateKantor() {
        var submitable = true;
        var errorNama;
        const fokusNama = this.state.namaKantorCabang
        if (fokusNama.length < 2) {
            submitable = false;
            errorNama = "Minimal terdapat 2 karakter";
        } 
        if (this.state.errorNama !== errorNama) {
            this.setState({
                errorNama: errorNama
            })
        }
        return submitable;
    }

    validateArea() {
        var submitable = true;
        var errorArea;
        const fokusArea = this.state.area
        if (fokusArea.length < 2) {
            submitable = false;
            errorArea = "Minimal terdapat 2 karakter";
        } 
        if (this.state.errorArea !== errorArea) {
            this.setState({
                errorArea: errorArea
            })
        }
        return submitable;
    }

    validateBM() {
        var submitable = true;
        var errorBM;
        const fokusBM = this.state.idPemilik
        if (fokusBM.length < 2) {
            submitable = false;
            errorBM = "Branch Manager harus diisi";
        } 
        if (this.state.errorBM !== errorBM) {
            this.setState({
                errorBM: errorBM
            })
        }
        return submitable;
    }


    validateRegional() {
        var submitable = true;
        var errorReg;
        const fokusReg = this.state.regional
        if (fokusReg.length < 2) {
            submitable = false;
            errorReg = "Minimal terdapat 2 karakter";
        } 
        if (this.state.errorReg!== errorReg) {
            this.setState({
                errorReg: errorReg
            })
        }
        return submitable;
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Nama Point*",
                    required: true,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "namaKantorCabang",
                    validation: this.state.errorNama,
                    value: this.state.namaKantorCabang,
                    placeholder: "Masukan nama point"
                }, {
                    label: "Branch Manger*",
                    required: true,
                    validation: this.state.errorBM,
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "idPemilik",
                    value: this.state.idPemilik,
                    optionList: this.state.employeeOptionList
                },{
                    label: "Area*",
                    required: true,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "area",
                    validation: this.state.errorArea,
                    value: this.state.area,
                    placeholder: "Masukan nama area"
                },{
                    label: "Regional*",
                    required: true,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "regional",
                    validation: this.state.errorReg,
                    value: this.state.regional,
                    placeholder: "Masukan nama regional"
                },{
                    label: "Kunjungan Audit*",
                    handleChange: this.handleInputChange,
                    type: "checkbox",
                    name: "kunjunganAudit",
                    value: this.state.kunjunganAudit,
                    checked: this.state.kunjunganAudit,
                }


            ]
        )
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
                    onClick={() => window.location.href = "/administrator/kantorCabang"}>
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
                    title="Form Tambah Kantor Cabang"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
             </>
        );
    }
}