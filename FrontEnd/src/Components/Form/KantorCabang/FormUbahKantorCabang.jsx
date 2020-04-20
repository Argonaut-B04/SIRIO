import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import KantorCabangService from '../../../Services/KantorCabangService';
import EmployeeService from '../../../Services/EmployeeService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import {OverlayTrigger, Button, Tooltip} from 'react-bootstrap'

/**
 * Kelas untuk membuat form demo
 */
class FormUbahKantorCabang extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            id: "",
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
        this.setRedirect = this.setRedirect.bind(this);
        this.renderDataKantor = this.renderDataKantor.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);

    }

    componentDidMount() {
        this.renderEmployeeOption();
        this.renderDataKantor();
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
                    editSuccess: true
                }
            }} />
        }
    };

    componentDidUpdate() {
        var submitable = true;

        submitable = this.validateKantor() && this.validateArea() && this.validateRegional() && this.validateBM();

        if (this.state.submitable !== submitable) {
            this.setState({
                submitable: submitable
            })
        }
    }

    validateKantor() {
        var submitable = true;
        var errorNama;
        const fokusNama = this.state.namaKantorCabang
        if(fokusNama.match(".*[-@#!$%^&*()_+{}:.,[]|>/=<?]+.*")){
            submitable = false;
            errorNama = "Hanya boleh mengandung huruf";
        }
        if (fokusNama.length < 2) {
            submitable = false;
            errorNama = "Minimal terdapat 2 karakter";
        } 
        if (fokusNama.length > 25) {
            submitable = false;
            errorNama = "Nama kantor tidak boleh lebih dari 25 karakter";
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
        if(fokusArea.match(".*[-@#!$%^&*()_+{}:.,[]|>/=<?]+.*")){
            submitable = false;
            errorArea = "Hanya boleh mengandung huruf";
        }
        if (fokusArea.length < 2) {
            submitable = false;
            errorArea = "Minimal terdapat 2 karakter";
        } 
        if (fokusArea.length > 125) {
            submitable = false;
            errorArea = "Area tidak boleh lebih dari 125 karakter";
        }
        if (this.state.errorArea !== errorArea) {
            this.setState({
                errorArea: errorArea
            })
        }
        return submitable;
    }

    validateRegional() {
        var submitable = true;
        var errorReg;
        const fokusReg = this.state.regional
        if(fokusReg.match(".*[-@#$!%^&*()_+{}:.,[]|>/=<?]+.*")){
            submitable = false;
            errorReg= "Hanya boleh mengandung huruf";
        }
        if (fokusReg.length < 2) {
            submitable = false;
            errorReg = "Minimal terdapat 2 karakter";
        } 
        if (fokusReg.length > 125) {
            submitable = false;
            errorReg = "Regional tidak boleh lebih dari 50 karakter";
        }
        if (this.state.errorReg!== errorReg) {
            this.setState({
                errorReg: errorReg
            })
        }
        return submitable;
    }

    validateBM() {
        var submitable = true;
        var errorBM;
        const fokusBM = this.state.idPemilik
        if (fokusBM.length < 1) {
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

    async renderDataKantor() {
        const response = await KantorCabangService.getKantorCabangDetail(this.props.location.state.id);

        this.setState({
            id: response.data.result.idKantor,
            namaKantorCabang: response.data.result.namaKantor,
            idPemilik: response.data.result.pemilik.idEmployee,
            area: response.data.result.area,
            regional: response.data.result.regional,
            kunjunganAudit: response.data.result.kunjunganAudit

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

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi unu wajib ada jika membuat field tipe select
    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    async handleSubmit(event) {
        event.preventDefault();
        if(this.state.submitable){
            const kantorCabang = {
                id: this.state.id,
                namaKantorCabang: this.state.namaKantorCabang,
                idPemilik: this.state.idPemilik,
                area: this.state.area,
                regional: this.state.regional,
                kunjunganAudit: this.state.kunjunganAudit
            }
            KantorCabangService.editKantorCabang(kantorCabang)
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
                        label: "Nama Point*",
                        handleChange: this.handleChange,
                        required: true,
                        type: "text",
                        name: "namaKantorCabang",
                        validation: this.state.errorNama,
                        value: this.state.namaKantorCabang,
                        placeholder: "Masukan nama point"
                    }, {
                        label: "Branch Manager*",
                        handleChange: this.handleSelectChange,
                        required: true,
                        type: "select",
                        name: "idPemilik",
                        value: this.state.idPemilik,
                        validation: this.state.errorBM,
                        optionList: this.state.employeeOptionList
                    },{
                        label: "Area*",
                        handleChange: this.handleChange,
                        required: true,
                        type: "text",
                        name: "area",
                        validation: this.state.errorArea,
                        value: this.state.area,
                        placeholder: "Masukan nama area"
                    },{
                        label: "Regional*",
                        handleChange: this.handleChange,
                        required: true,
                        type: "text",
                        name: "regional",
                        value: this.state.regional,
                        validation: this.state.errorReg,
                        placeholder: "Masukan nama regional"
                    },{
                        label: <OverlayTrigger
                        placement="right"
                        overlay={<Tooltip id="button-tooltip">
                        Sudah pernah atau belum pernah dikunjungi audit
                        </Tooltip>}
                        >
                        <p variant="success">Kunjungan Audit</p>
                        </OverlayTrigger>,
                        handleChange: this.handleInputChange,
                        required: true,
                        type: "checkbox",
                        name: "kunjunganAudit",
                        value: this.state.kunjunganAudit,
                        checked: this.state.kunjunganAudit,
                    }
    
    
                ]
            )
        //}
        
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
                    type="button"
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
                    title="Form Ubah Kantor Cabang"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
             </>
        );
    }
}

export default withRouter(FormUbahKantorCabang);