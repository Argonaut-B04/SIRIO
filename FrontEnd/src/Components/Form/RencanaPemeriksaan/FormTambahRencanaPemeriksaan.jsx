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
            namaRencana: "",
            linkMajelis: "",
            status: 1,
            kantorOptionList: [],
            employeeOptionList: [],
            tugasPemeriksaanList: [{
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
        console.log(event);
        console.log(index);
        const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        tugasPemeriksaanList[index][event.target.name] = event.target.value;

        this.setState(
            {
                tugasPemeriksaanList: tugasPemeriksaanList
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi ini wajib ada jika membuat field tipe select
    handleMultipleSelectChange(name, target, index) {
        // const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        // tugasPemeriksaanList[index][event.target.name] = event.target.value;
        console.log(name);
        console.log(target);
        console.log(index);
        const formList = this.state.tugasPemeriksaanList;
        formList[index][name] = target.value;
        this.setState(
            {
                //tugasPemeriksaanList: tugasPemeriksaanList,
                tugasPemeriksaanList: formList
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
                tugasPemeriksaanList: [{
                    kantorCabang: this.state.kantorCabang,
                    idQA: this.state.idQA,
                    tanggalMulai: this.state.tanggalMulai,
                    tanggalSelesai: this.state.tanggalSelesai,
                }]
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
                tugasPemeriksaanList: [{
                    kantorCabang: this.state.kantorCabang,
                    idQA: this.state.idQA,
                    tanggalMulai: this.state.tanggalMulai,
                    tanggalSelesai: this.state.tanggalSelesai,
                }]
            }
            RencanaPemeriksaanService.addRencanaPemeriksaan(rencanaPemeriksaan)
            .then(() => this.setRedirect());
        }

    }

    fullComponentInside() {
        const forms = [];
        for (let i = 0; i < this.state.tugasPemeriksaanList.length; i++) {
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
                    value: this.state.namaRencana,
                    placeholder: "Masukan nama rencana"
                }, {
                    label: "Link Pemeriksaan*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "linkMajelis",
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
                    type: "select",
                    name: "kantorCabang",
                    value: this.state.tugasPemeriksaanList[index].kantorCabang,
                    optionList: this.state.kantorOptionList
                }, {
                    label: "QA Officer*",
                    handleChange: this.handleMultipleSelectChange,
                    index: index,
                    type: "select",
                    name: "idQA",
                    value: this.state.tugasPemeriksaanList[index].idQA,
                    optionList: this.state.employeeOptionList
                }, {
                    label: "Tanggal Mulai*",
                    handleChange: this.handleMultipleChange,
                    index: index,
                    type: "date",
                    name: "tanggalMulai",
                    value: this.state.tugasPemeriksaanList[index].tanggalMulai,
                    placeholder: "Masukan tanggal mulai"
                }, {
                    label: "Tanggal Selesai*",
                    handleChange: this.handleMultipleChange,
                    index: index,
                    type: "date",
                    name: "tanggalSelesai",
                    value: this.state.tugasPemeriksaanList[index].tanggalSelesai,
                    placeholder: "Masukan tanggal selesai"
                }

            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                    classes="mx-2"
                    onClick={(event) => this.handleSubmit(event,"simpan")}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    onClick={(event) => this.handleSubmit(event,"draft")}>
                    Draft
                </SirioButton>
                <SirioButton purple
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
        const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        const newtugasPemeriksaanList = this.deleteItem(tugasPemeriksaanList, index)

        this.setState(
            {
                tugasPemeriksaanList: newtugasPemeriksaanList
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
        const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        tugasPemeriksaanList.push({});

        this.setState(
            {
                tugasPemeriksaanList: tugasPemeriksaanList
            }
        )
    }

    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Tambah Rencana Pemeriksaan"
                    subtitle="Tugas Pemeriksaan"
                    inputDefinition={this.outerInputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />

            </>

        );
    }
}