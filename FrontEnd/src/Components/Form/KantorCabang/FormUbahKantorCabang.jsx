import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import KantorCabangService from '../../../Services/KantorCabangService';
import EmployeeService from '../../../Services/EmployeeService';
import { NavLink, Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import {OverlayTrigger, Tooltip} from 'react-bootstrap'


class FormUbahKantorCabang extends React.Component {

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
            redirect: false
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderDataKantor = this.renderDataKantor.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.submitable = this.submitable.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

    }

    componentDidMount() {
        this.renderDataKantor();
    }

    validateKantor(fokusNama) {
        var errorNama = "";
        var symbols = /[-!$%@#^&*()\\_+|~=`{}\[\]:";'<>?,.\/]/;
        if (fokusNama === null || fokusNama === "") {
            errorNama = "Nama kantor harus diisi";
        } else if (fokusNama.match(symbols)) {
            errorNama = "Nama hanya boleh mengandung huruf dan angka";
        }else if (fokusNama.length > 25) {
            errorNama = "Nama kantor tidak boleh lebih dari 25 karakter";
        }
       
        this.setState({
            errorNama: errorNama
        })
        
    }

    validateArea(fokusArea) {
        var errorArea = "";
        var symbols = /[-!$%@#^&*()\\_+|~=`{}\[\]:";'<>?,.\/]/;
        if(fokusArea === null || fokusArea === ""){
            errorArea = "Area harus diisi";
        } else if (fokusArea.match(symbols)) {
            errorArea = "Area hanya boleh mengandung huruf dan angka";
        }
        if (fokusArea.length > 125) {
            errorArea = "Area tidak boleh lebih dari 125 karakter";
        }
        
        this.setState({
            errorArea: errorArea
        })
        
    }

    validateRegional(fokusReg) {
        var errorReg = "";
        var symbols = /[-!$%@#^&*()\\_+|~=`{}\[\]:";'<>?,.\/]/;
        if(fokusReg === null || fokusReg === ""){
            errorReg= "Regional harus diisi";
        } else if (fokusReg.match(symbols)) {
            errorReg= "Regional hanya boleh mengandung huruf dan angka";
        }
        if (fokusReg.length > 125) {
            errorReg = "Regional tidak boleh lebih dari 125 karakter";
        }
       
        this.setState({
            errorReg: errorReg
        })
       
    }

    submitable() {
        return this.state.errorNama === "" &&
            this.state.errorArea === "" &&
            this.state.errorReg === "" &&
            (this.state.namaKantorCabang !== null && this.state.namaKantorCabang !== "") &&
            (this.state.area !== null && this.state.area !== "") &&
            (this.state.idPemilik!== null && this.state.idPemilik !== "") &&
            (this.state.regional !== null && this.state.regional !== "");
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/kantorCabang",
                state: {
                    editSuccess: true
                }
            }} />
        }
    };

    async renderDataKantor() {

        const responseEmployee = await EmployeeService.getAllBM();

        const employeeOptionList = responseEmployee.data.result.map(employee => {
            return (
                {
                    label: employee.nama,
                    value: employee.idEmployee
                }
            )
        });
        
        const response = await KantorCabangService.getKantorCabangDetail(this.props.location.state.id);

        this.setState({
            employeeOptionList: employeeOptionList,
            id: response.data.result.idKantor,
            namaKantorCabang: response.data.result.namaKantor,
            idPemilik: response.data.result.pemilik.idEmployee,
            area: response.data.result.area,
            regional: response.data.result.regional,
            kunjunganAudit: response.data.result.kunjunganAudit,
            errorNama: "",
            errorArea: "",
            errorReg: "",

        });
    }
    
    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        const { name, value } = event.target;
        this.setState(
            {
                [name]
                    : value
            }
        );

        switch (name) {
            case "namaKantorCabang":
                this.validateKantor(value);
                break;
            case "area":
                this.validateArea(value);
                break;
            case "regional":
                this.validateRegional(value);
                break;
        }
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
    // Fungsi ini wajib ada jika membuat field tipe select
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
        if (this.submitable()) {
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
                        label: "Nama Point",
                        handleChange: this.handleChange,
                        required: true,
                        type: "text",
                        name: "namaKantorCabang",
                        errormessage: this.state.errorNama,
                        value: this.state.namaKantorCabang,
                        placeholder: "Masukan nama point"
                    }, {
                        label: "Branch Manager",
                        handleChange: this.handleSelectChange,
                        required: true,
                        type: "select",
                        name: "idPemilik",
                        value: this.state.idPemilik,
                        errormessage: this.state.errorBM,
                        optionList: this.state.employeeOptionList
                    },{
                        label: "Area",
                        handleChange: this.handleChange,
                        required: true,
                        type: "text",
                        name: "area",
                        errormessage: this.state.errorArea,
                        value: this.state.area,
                        placeholder: "Masukan nama area"
                    },{
                        label: "Regional",
                        handleChange: this.handleChange,
                        required: true,
                        type: "text",
                        name: "regional",
                        value: this.state.regional,
                        errormessage: this.state.errorReg,
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
                    pathname: "/kantorCabang/detail",
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