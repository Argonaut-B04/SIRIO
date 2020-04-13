import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import EmployeeService from '../../../Services/EmployeeService';
import KantorCabangService from '../../../Services/KantorCabangService';
import { Redirect } from 'react-router-dom';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';

/**
 * Kelas untuk membuat form demo
 */
export default class FormTambahRencana extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            redirect: false,
            submitable: false,
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
        this.handleMultipleChange = this.handleMultipleChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderEmployeeOption = this.renderEmployeeOption.bind(this);
        this.renderKantorOption = this.renderKantorOption.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
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
                pathname: "/manager/rencanaPemeriksaan",
                state: {
                    addSuccess: true
                }
            }} />
        }
    };

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

    componentDidUpdate() {
        var submitable = true;

        submitable = this.validateNama() && this.validateLink();

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

    validateLink() {
        var submitable = true;
        var errorLink;
        const fokusLink = this.state.linkMajelis
        if (!fokusLink.includes("http://") ) {
            submitable = false;
            errorLink = "Link harus mengandung 'http://' ";
        } 
        if (this.state.errorLink !== errorLink) {
            this.setState({
                errorLink: errorLink
            })
        }
        return submitable;
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

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    handleSubmit(event, nama) {
        if(nama == "simpan"){
            event.preventDefault();
            const rencanaPemeriksaan = {
                namaRencana: this.state.namaRencana,
                linkMajelis: this.state.linkMajelis,
                status: 2,
                daftarTugasPemeriksaan: this.state.daftarTugasPemeriksaan
            }
            RencanaPemeriksaanService.addRencanaPemeriksaan(rencanaPemeriksaan)
            .then(() => this.setRedirect());
        }
        else if (nama == "draft"){
            event.preventDefault();
            const rencanaPemeriksaan = {
                namaRencana: this.state.namaRencana,
                linkMajelis: this.state.linkMajelis,
                status: this.state.status,
                daftarTugasPemeriksaan: this.state.daftarTugasPemeriksaan
            }
            RencanaPemeriksaanService.addRencanaPemeriksaan(rencanaPemeriksaan)
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
                    required: true,
                    validation: this.state.errorNama,
                    value: this.state.namaRencana,
                    placeholder: "Masukan nama rencana"
                }, {
                    label: "Link Pemeriksaan*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "linkMajelis",
                    required: true,
                    validation: this.state.errorLink,
                    value: this.state.linkMajelis,
                    placeholder: "Masukan link pemeriksaan"
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
                    value: this.state.daftarTugasPemeriksaan[index].idQA,
                    optionList: this.state.employeeOptionList
                }, {
                    label: "Tanggal Mulai*",
                    handleChange: this.handleMultipleChange,
                    index: index,
                    type: "date",
                    required: true,
                    name: "tanggalMulai",
                    value: this.state.daftarTugasPemeriksaan[index].tanggalMulai
                }, {
                    label: "Tanggal Selesai*",
                    handleChange: this.handleMultipleChange,
                    index: index,
                    type: "date",
                    required: true,
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
                    recommended={this.state.submitable}
                    disabled={!this.state.submitable}
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event,"simpan")}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event,"draft")}>
                    Draft
                </SirioButton>
                <SirioButton purple
                    classes="mx-1"
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
                <SirioButton
                    red
                    type="button"
                    onClick={() => this.deleteChildForm(index)}
                >
                    Hapus
                </SirioButton>
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