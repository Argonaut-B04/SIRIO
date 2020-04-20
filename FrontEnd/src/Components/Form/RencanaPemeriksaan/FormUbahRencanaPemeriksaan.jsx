import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";
import EmployeeService from '../../../Services/EmployeeService';
import KantorCabangService from '../../../Services/KantorCabangService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';


/**
 * Kelas untuk membuat form demo
 */
class FormUbahRencana extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            redirect: false,
            submitable: true,
            id: "",
            namaRencana: "",
            linkMajelis: "",
            status: "",
            kantorOptionList: [],
            employeeOptionList: [],
            daftarTugasPemeriksaan: [{
                kantorCabang: "",
                idQA: "",
                tanggalMulai: "",
                tanggalSelesai: "",
            }]
        }

        this.handleChange = this.handleChange.bind(this);
        this.innerInputDefinition = this.innerInputDefinition.bind(this);
        this.handleMultipleSelectChange = this.handleMultipleSelectChange.bind(this);
        this.handleMultipleChange = this.handleMultipleChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderEmployeeOption = this.renderEmployeeOption.bind(this);
        this.renderKantorOption = this.renderKantorOption.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.renderDataRencana = this.renderDataRencana.bind(this);
    }

    componentDidMount() {
        this.renderEmployeeOption();
        this.renderKantorOption();
        this.renderDataRencana();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/manager/rencanaPemeriksaan",
                state: {
                    editSuccess: true
                }
            }} />
        }
    };

    async renderDataRencana() {
        const response = await RencanaPemeriksaanService.getRencanaPemeriksaanDetail(this.props.location.state.id);
        const result = response.data.result;

        for (var i = 0;i < result.daftarTugasPemeriksaan.length; i++) {
            const tanggalMulai = result.daftarTugasPemeriksaan[i].tanggalMulai;
            const tanggalSelesai = result.daftarTugasPemeriksaan[i].tanggalSelesai;
            result.daftarTugasPemeriksaan[i].tanggalMulai = tanggalMulai.split(" ")[0];
            result.daftarTugasPemeriksaan[i].tanggalSelesai = tanggalSelesai.split(" ")[0];
        }

        this.setState({
            id: response.data.result.id,
            namaRencana: response.data.result.namaRencana,
            linkMajelis: response.data.result.linkMajelis,
            status: response.data.result.status,
            daftarTugasPemeriksaan: result.daftarTugasPemeriksaan
        })
    }

    handleMultipleChange(event, index) {
        const daftarTugasPemeriksaan = this.state.daftarTugasPemeriksaan
        daftarTugasPemeriksaan[index][event.target.name] = event.target.value;

        this.setState(
            {
                daftarTugasPemeriksaan: daftarTugasPemeriksaan
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi ini wajib ada jika membuat field tipe select
    handleMultipleSelectChange(name, target, index) {
        console.log(index);
        const formList = this.state.daftarTugasPemeriksaan;
        formList[index][name] = target.value;
        this.setState(
            {
                daftarTugasPemeriksaan: formList
            }
        )
    }


    async renderEmployeeOption() {
        const response = await EmployeeService.getAllQAOfficer();

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

    async renderKantorOption() {
        const response = await KantorCabangService.getKantorCabangList();

        const kantorOptionList = response.data.result.map(kantorCabang => {
            return (
                {
                    label: kantorCabang.namaKantor,
                    value: kantorCabang.idKantor
                }
            )
        });

        this.setState({
            kantorOptionList: kantorOptionList
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

    componentDidUpdate() {
        var submitable = true;
        if (this.state.daftarTugasPemeriksaan[0] == null){
            submitable = this.validateNama() && this.validateLink() 
            
        }else{
            submitable = this.validateNama() && this.validateLink() && this.validateKC() && this.validateQA() && this.validateTanggalMulai() && this.validateTanggalSelesai()  
        }
        if (this.state.submitable !== submitable) {
            this.setState({
                submitable: submitable
            })
        }
    }

    validateNama() {
        var submitable = true;
        var errorNama;
        const fokusNama = this.state.namaRencana
        if(fokusNama.match(".*[1234567890!-@#$%^&*()_+{}:.,[]|>/=<?]+.*")){
            submitable = false;
            errorNama = "Hanya boleh mengandung huruf";
        }
        if (fokusNama.length < 2) {
            submitable = false;
            errorNama = "Minimal terdapat 2 karakter";
        } 
        if (fokusNama.length > 50) {
            submitable = false;
            errorNama = "Nama rencana tidak boleh lebih dari 50 karakter";
        }
        if (this.state.errorNama !== errorNama) {
            this.setState({
                errorNama: errorNama
            })
        }
        return submitable;
    }

    validateLink() {
        var submitable = true;
        var errorLink;
        const fokusLink = this.state.linkMajelis
        if (fokusLink.length < 1) {
            submitable = false;
            errorLink = "Lampiran wajib diisi";
        } else if (!fokusLink.includes("https://")) {
            submitable = false;
            errorLink = "Lampiran harus berupa link url";
        }
        if (fokusLink.length > 255) {
            submitable = false;
            errorLink = "Link tidak boleh lebih dari 255 karakter";
        }
        if (this.state.errorLink !== errorLink) {
            this.setState({
                errorLink: errorLink
            })
        }
        return submitable;
    }

    validateKC() {
        var submitable = true;
        var errorKC;
        if (this.state.daftarTugasPemeriksaan[0] != null){
            for (let i = 0; i < this.state.daftarTugasPemeriksaan.length;i++) {
                const fokusKC = this.state.daftarTugasPemeriksaan[i].kantorCabang
                if (fokusKC == null) {
                    submitable = false;
                    errorKC = "Kantor Cabang harus diisi";
                } 
            }
        }
        if (this.state.errorKC !== errorKC) {
            this.setState({
                errorKC: errorKC
            })
        }
        return submitable;
    }

    validateQA() {
        var submitable = true;
        var errorQA;
        if (this.state.daftarTugasPemeriksaan[0] != null){
            for (let i = 0; i < this.state.daftarTugasPemeriksaan.length;i++) {
                const fokusQA = this.state.daftarTugasPemeriksaan[i].idQA
                if (fokusQA == null) {
                    submitable = false;
                    errorQA = "QA Officer harus diisi";
                } 
            }
        }
        if (this.state.errorQA !== errorQA) {
            this.setState({
                errorQA: errorQA
            })
        }
        return submitable;
    }

    validateTanggalMulai() {
        var submitable = true;
        var errorTM;
        if (this.state.daftarTugasPemeriksaan[0] != null){
            for (let i = 0; i < this.state.daftarTugasPemeriksaan.length;i++) {
                const fokusTM = this.state.daftarTugasPemeriksaan[i].tanggalMulai
                if (fokusTM == null) {
                    submitable = false;
                    errorTM = "Tanggal Mulai harus diisi";
                } 
            }
        }
        if (this.state.errorTM !== errorTM) {
            this.setState({
                errorTM: errorTM
            })
        }
        return submitable;
    }

    validateTanggalSelesai() {
        var submitable = true;
        var errorTS;
        if (this.state.daftarTugasPemeriksaan[0] != null){
            for (let i = 0; i < this.state.daftarTugasPemeriksaan.length;i++) {
                const fokusTS = this.state.daftarTugasPemeriksaan[i].tanggalSelesai
                if (fokusTS == null) {
                    submitable = false;
                    errorTS = "Tanggal Selesai harus diisi";
                } 
            }
        }
        if (this.state.errorTS !== errorTS) {
            this.setState({
                errorTS: errorTS
            })
        }
        return submitable;
    }

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    async handleSubmit(event, nama) {
        if(nama == "simpan"){
            event.preventDefault();
            for (let i = 0; i < this.state.daftarTugasPemeriksaan.length;i++){
                console.log(this.state.daftarTugasPemeriksaan[i].tanggalMulai)
                const isTglError = this.state.daftarTugasPemeriksaan[i].tanggalMulai > this.state.daftarTugasPemeriksaan[i].tanggalSelesai;
                if(isTglError){
                    const errorTM = "Tanggal mulai harus lebih kecil daripada tanggal selesai";
                    this.state.submitable = false
                    if (this.state.errorTM !== errorTM) {
                        this.state.submitable = false
                        this.setState({
                            errorTM: errorTM

                        })
                    }
                }
                else if(this.state.submitable){
                        const rencanaPemeriksaan = {
                            id: this.state.id,
                            namaRencana: this.state.namaRencana,
                            linkMajelis: this.state.linkMajelis,
                            status: 2,
                            daftarTugasPemeriksaan: this.state.daftarTugasPemeriksaan
                        }
                        RencanaPemeriksaanService.editRencanaPemeriksaan(rencanaPemeriksaan)
                        .then(() => this.setRedirect());
                }
            }
            // event.preventDefault();
            // const rencanaPemeriksaan = {
            //     id: this.state.id,
            //     namaRencana: this.state.namaRencana,
            //     linkMajelis: this.state.linkMajelis,
            //     status: 2,
            //     daftarTugasPemeriksaan: this.state.daftarTugasPemeriksaan
            // }
            // RencanaPemeriksaanService.editRencanaPemeriksaan(rencanaPemeriksaan)
            // .then(() => this.setRedirect());
        }
        else if (nama == "draft"){
            event.preventDefault();
            const rencanaPemeriksaan = {
                id: this.state.id,
                namaRencana: this.state.namaRencana,
                linkMajelis: this.state.linkMajelis,
                status: this.state.status,
                daftarTugasPemeriksaan: this.state.daftarTugasPemeriksaan
            }
            RencanaPemeriksaanService.editRencanaPemeriksaan(rencanaPemeriksaan)
            .then(() => this.setRedirect());
        }

    }

    fullComponentInside() {
        const forms = [];
        for (let i = 0; i < this.state.daftarTugasPemeriksaan.length; i++) {
            forms.push(
                <SirioForm
                    subtitle="Tugas Pemeriksaan"
                    key={i}
                    childForm
                    id={i}
                    inputDefinition={this.innerInputDefinition(i)}
                    footerButton={this.childFooterButton(i)}
                />
            )
        }
        return (
            <>
                {forms.map(form => form)}
            </>
        )
    }

    outerInputDefinition() {
        return (
            [
                {
                    label: "Nama Rencana*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "namaRencana",
                    validation: this.state.errorNama,
                    value: this.state.namaRencana,
                    placeholder: "Masukan nama rencana"
                }, {
                    label: "Link Pemeriksaan*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "linkMajelis",
                    validation: this.state.errorLink,
                    value: this.state.linkMajelis,
                    placeholder: "https://drive.google.com/"
                },{
                    fullComponent:
                        this.tambahTugasButton
                }, {
                    fullComponent:
                        this.fullComponentInside()
                } 

            ]
        )
    }

    tambahTugasButton = (
        <SirioButton blue recommended
            classes="mr-3"
            onClick={() => this.addForm()}
            type="button"
        >
            Tambah Tugas
        </SirioButton>
    )

    innerInputDefinition(index) {
        return (
            [
                
                {
                    label: "Kantor Cabang*",
                    handleChange: this.handleMultipleSelectChange,
                    index: index,
                    required: true,
                    validation: this.state.errorKC,
                    type: "select",
                    name: "kantorCabang",
                    value: this.state.daftarTugasPemeriksaan[index].kantorCabang,
                    optionList: this.state.kantorOptionList
                }, {
                    label: "QA Officer*",
                    handleChange: this.handleMultipleSelectChange,
                    index: index,
                    type: "select",
                    name: "idQA",
                    required: true,
                    validation: this.state.errorQA,
                    value: this.state.daftarTugasPemeriksaan[index].idQA,
                    optionList: this.state.employeeOptionList
                }, {
                    label: "Tanggal Mulai*",
                    handleChange: this.handleMultipleChange,
                    index: index,
                    type: "date",
                    required: true,
                    validation: this.state.errorTM,
                    name: "tanggalMulai",
                    value: this.state.daftarTugasPemeriksaan[index].tanggalMulai
                }, {
                    label: "Tanggal Selesai*",
                    handleChange: this.handleMultipleChange,
                    index: index,
                    type: "date",
                    required: true,
                    validation: this.state.errorTS,
                    name: "tanggalSelesai",
                    value: this.state.daftarTugasPemeriksaan[index].tanggalSelesai
                }

            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple 
                    recommended={this.state.submitable && this.state.daftarTugasPemeriksaan[0] != null}
                    disabled={!this.state.submitable || !(this.state.daftarTugasPemeriksaan[0] != null)} 
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event,"simpan")}>
                    Jalankan
                </SirioButton>
                <SirioButton purple
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event,"draft")}>
                    Draft
                </SirioButton>
                <SirioButton purple
                    classes="mx-1"
                    type="button"
                    onClick={() => window.location.href = "/manager/rencanaPemeriksaan"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    deleteItem(array, index) {
        const toReturn = []
        for (var row in array) {
            // eslint-disable-next-line
            if (row != index) {
                toReturn.push(array[row]);
            }
        }
        return toReturn;
    }


    deleteChildForm(index) {
        const daftarTugasPemeriksaan = this.state.daftarTugasPemeriksaan
        const newdaftarTugasPemeriksaan = this.deleteItem(daftarTugasPemeriksaan, index)

        this.setState(
            {
                daftarTugasPemeriksaan: newdaftarTugasPemeriksaan
            }
        )
    }

    childFooterButton(index) {
        return (
            <div>
                 <SirioWarningButton
                        red
                        type="button"
                        modalTitle="Konfirmasi Penghapusan"
                        modalDesc="Apakah anda yakin untuk menghapus tugas pemeriksaan?"
                        onConfirm={() => this.deleteChildForm(index)}
                        customConfirmText="Ya, Hapus"
                        customCancelText="Batal"
                    >
                        Hapus
                    </SirioWarningButton>
            </div>
        )
    }

    addForm() {
        const daftarTugasPemeriksaan = this.state.daftarTugasPemeriksaan
        daftarTugasPemeriksaan.push({});

        this.setState(
            {
                daftarTugasPemeriksaan: daftarTugasPemeriksaan
            }
        )
    }

    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Ubah Rencana Pemeriksaan"
                    inputDefinition={this.outerInputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />

            </>

        );
    }
}

export default withRouter(FormUbahRencana);