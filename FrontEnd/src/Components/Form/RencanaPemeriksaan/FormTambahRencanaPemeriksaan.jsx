import React from 'react';
import SirioForm from '../SirioForm';
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";
import SirioButton from '../../Button/SirioButton';
import EmployeeService from '../../../Services/EmployeeService';
import KantorCabangService from '../../../Services/KantorCabangService';
import { Redirect } from 'react-router-dom';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';
import moment from 'moment';

export default class FormTambahRencana extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            redirect: false,
            namaRencana: "",
            linkMajelis: "",
            status: 1,
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
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderEmployeeOption = this.renderEmployeeOption.bind(this);
        this.renderKantorOption = this.renderKantorOption.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.submitableSimpan = this.submitableSimpan.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    componentDidMount() {
        this.renderEmployeeOption();
        this.renderKantorOption();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/rencana-pemeriksaan",
                state: {
                    addSuccess: true
                }
            }} />
        }
    };

    handleSelectChange(event, index) {
        const daftarTugasPemeriksaan = this.state.daftarTugasPemeriksaan
        daftarTugasPemeriksaan[index][event.target.name] = event.target.value;

        if (event.target.name === "tanggalMulai") {
            daftarTugasPemeriksaan[index].tanggalSelesai = ""
        }
    
        this.setState(
            {
                daftarTugasPemeriksaan: daftarTugasPemeriksaan
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi ini wajib ada jika membuat field tipe select
    handleMultipleSelectChange(name, target, index) {
      
        const formList = this.state.daftarTugasPemeriksaan;
        formList[index][name] = target.value;
        this.setState(
            {
                daftarTugasPemeriksaan: formList
            }
        )

    }

    validateNama(fokusNama) {
        var errorNama = "";
        var letterOnly = /^[a-zA-Z\s]*$/;
        if (fokusNama === null || fokusNama === "") {
            errorNama = "Nama rencana harus diisi";
        } else if (!fokusNama.match(letterOnly)) {
            errorNama = "Nama hanya boleh mengandung huruf";
        }else if (fokusNama.length > 25) {
            errorNama = "Nama rencana tidak boleh lebih dari 25 karakter";
        }
       
        this.setState({
            errorNama: errorNama
        })
        
    }

    validateLink(fokusLink) {
        var errorLink = "";

        // eslint-disable-next-line
        var link = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.%]+$/;
        if (fokusLink === null || fokusLink === "") {
            errorLink = "Lampiran wajib diisi";
        } else if (!fokusLink.match(link)) {
            errorLink = "Lampiran tidak sesuai format link url";
        } else if (fokusLink.length > 255) {
            errorLink = "Link tidak boleh lebih dari 255 karakter";
        }
        
        this.setState({
            errorLink: errorLink
        })
       
    }

    submitable() {
        return this.state.errorNama === "" &&
        this.state.errorLink === "" &&
        (this.state.namaRencana !== null && this.state.namaRencana !== "") &&
        (this.state.linkMajelis !== null && this.state.linkMajelis !== "");
    }

    submitableSimpan() {
        var submitable = true;
        if(this.state.daftarTugasPemeriksaan.length > 0){
            this.state.daftarTugasPemeriksaan.map(tugas => {
                submitable = submitable && 
                    (tugas.kantorCabang !== null && tugas.kantorCabang !== "" && tugas.kantorCabang !== undefined) &&
                    (tugas.idQA !== null && tugas.idQA !== "" && tugas.idQA !== undefined) &&
                    (tugas.tanggalMulai !== null && tugas.tanggalMulai !== ""  && tugas.tanggalMulai !== undefined) &&
                    (tugas.tanggalSelesai !== null && tugas.tanggalSelesai !== ""  && tugas.tanggalSelesai !== undefined);
                return null
            });
        }else {
            submitable = false;
        }
        return submitable;
    }

    async renderEmployeeOption() {
        this.props.contentStartLoading();

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
        }, this.props.contentFinishLoading())
    }

    async renderKantorOption() {
        this.props.contentStartLoading();

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
            case "namaRencana":
                this.validateNama(value);
                break;
            case "linkMajelis":
                this.validateLink(value);
                break;
        }
    }

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    async handleSubmit(event, nama) {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");

        event.preventDefault();
        if(this.submitable()){
            RencanaPemeriksaanService.isExistRencana(this.state.namaRencana)
            .then(response => {
                if (response.data.result) {
                    this.setState({
                        errorNama: "Nama rencana sudah ada di database"
                    })
                } else {
                    if(nama === "simpan" && this.submitableSimpan()){
                        const rencanaPemeriksaan = {
                            namaRencana: this.state.namaRencana,
                            linkMajelis: this.state.linkMajelis,
                            status: 2,
                            daftarTugasPemeriksaan: this.state.daftarTugasPemeriksaan
                        }
                        RencanaPemeriksaanService.addRencanaPemeriksaan(rencanaPemeriksaan)
                        .then(() => this.setRedirect());
                       
                    }   else if (nama === "draft"){
                        const rencanaPemeriksaan = {
                            namaRencana: this.state.namaRencana,
                            linkMajelis: this.state.linkMajelis,
                            status: 1,
                            daftarTugasPemeriksaan: this.state.daftarTugasPemeriksaan
                        }
                        RencanaPemeriksaanService.addRencanaPemeriksaan(rencanaPemeriksaan)
                        .then(() => this.setRedirect());
                    }
                }
            });
        }

        this.props.contentFinishLoading()

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
                    label: "Nama Rencana",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "namaRencana",
                    required: true,
                    errormessage: this.state.errorNama,
                    value: this.state.namaRencana,
                    placeholder: "Masukan nama rencana"
                }, {
                    label: "Link Pemeriksaan",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "linkMajelis",
                    required: true,
                    errormessage: this.state.errorLink,
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
            classes="my-3"
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
                    label: "Kantor Cabang",
                    handleChange: this.handleMultipleSelectChange,
                    index: index,
                    type: "select",
                    required: true,
                    name: "kantorCabang",
                    value: this.state.daftarTugasPemeriksaan[index].kantorCabang,
                    optionList: this.state.kantorOptionList
                }, {
                    label: "QA Officer",
                    handleChange: this.handleMultipleSelectChange,
                    index: index,
                    type: "select",
                    name: "idQA",
                    required: true,
                    value: this.state.daftarTugasPemeriksaan[index].idQA,
                    optionList: this.state.employeeOptionList,
                }, {
                    label: "Tanggal Mulai",
                    handleChange: this.handleSelectChange,
                    index: index,
                    type: "date",
                    required: true,
                    name: "tanggalMulai",
                    value: this.state.daftarTugasPemeriksaan[index].tanggalMulai
                    
                }, {
                    label: "Tanggal Selesai",
                    handleChange: this.handleSelectChange,
                    index: index,
                    type: "date",
                    min: this.getMin(index),
                    required: this.state.daftarTugasPemeriksaan[index].tanggalMulai !== "",
                    name: "tanggalSelesai",
                    value: this.state.daftarTugasPemeriksaan[index].tanggalSelesai
                }

            ]
        )
    }

    getMin(index) {
        var dateToAdd = this.state.daftarTugasPemeriksaan[index].tanggalMulai;
        var date = new Date(dateToAdd);
        var after = moment(date).add(1, 'days').format('YYYY[-]MM[-]DD');
        return after;
    }

    submitButton() {
        var tombolJalankan =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Jalankan
            </SirioButton>;
        if (this.submitableSimpan() && this.submitable()) {
            tombolJalankan =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event)  => this.handleSubmit(event, "simpan")}
                >
                    Jalankan
                </SirioButton>
        }
        var tombolDraft =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Draft
            </SirioButton>;
        if (this.submitable()) {
            tombolDraft =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event)  => this.handleSubmit(event, "draft")}
                >
                    Draft
                </SirioButton>
        }
        return (
            <div>
                {tombolJalankan}
                {tombolDraft}
                <SirioButton purple
                    classes="mx-1"
                    onClick={() => window.location.href = "/rencana-pemeriksaan"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    deleteItem(array, index) {
        const toReturn = array.slice(0, index).concat(array.slice(index + 1, array.length));

        return toReturn;
    }


    deleteChildForm(index) {
        const daftarTugasPemeriksaan= this.state.daftarTugasPemeriksaan
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
                    title="Form Tambah Rencana Pemeriksaan"
                    inputDefinition={this.outerInputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />

            </>

        );
    }
}