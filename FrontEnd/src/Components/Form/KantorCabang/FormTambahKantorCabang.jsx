import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import KantorCabangService from '../../../Services/KantorCabangService';
import EmployeeService from '../../../Services/EmployeeService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import {OverlayTrigger, Tooltip} from 'react-bootstrap';


class FormTambahKantorCabang extends React.Component {

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
        }

        this.renderEmployeeOption = this.renderEmployeeOption.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    componentDidMount() {
        this.renderEmployeeOption();
    }

    validateKantor(fokusNama) {
        var errorNama = "";

        // eslint-disable-next-line
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

        // eslint-disable-next-line
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

        // eslint-disable-next-line
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
                pathname: "/kantor-cabang",
                state: {
                    addSuccess: true
                }
            }} />
        }
    };

    async renderEmployeeOption() {
        this.props.contentStartLoading();

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
        }, this.props.contentFinishLoading())
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

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    async handleSubmit(event) {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");

        event.preventDefault();
        if(this.submitable()){
            const response = await KantorCabangService.isExistKantorCabang(this.state.namaKantorCabang);
            if(response.data.result){
                const errorNama = "Nama kantor sudah ada di database";
                if (this.state.errorNama !== errorNama) {
                    this.setState({
                        errorNama: errorNama
                    })
                }
            }
            else{
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
        }

        this.props.contentFinishLoading()
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Nama Point",
                    required: true,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "namaKantorCabang",
                    errormessage: this.state.errorNama,
                    value: this.state.namaKantorCabang,
                    placeholder: "Masukan nama point"
                }, {
                    label: "Branch Manager",
                    required: true,
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "idPemilik",
                    value: this.state.idPemilik,
                    optionList: this.state.employeeOptionList
                },{
                    label: "Area",
                    required: true,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "area",
                    validation: this.state.errorArea,
                    value: this.state.area,
                    placeholder: "Masukan nama area"
                },{
                    label: "Regional",
                    required: true,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "regional",
                    validation: this.state.errorReg,
                    value: this.state.regional,
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
                    checked: this.state.kunjunganAudit
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
                <SirioButton purple
                    classes="mx-1"
                    onClick={() => window.location.href = "/kantor-cabang"}>
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

export default withRouter (FormTambahKantorCabang);