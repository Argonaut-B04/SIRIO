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
            status:1,
            kantorOptionList:[],
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

    handleMultipleChange(event, id) {
        const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        tugasPemeriksaanList[id][event.target.name] = event.target.value;
    
        this.setState(
            {
                tugasPemeriksaanList: tugasPemeriksaanList
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi ini wajib ada jika membuat field tipe select
    handleMultipleSelectChange(name, event, id) {
        const tugasPemeriksaanList = this.state.tugasPemeriksaanList
        tugasPemeriksaanList[id][event.target.name] = event.target.value;
        this.setState(
            {
                tugasPemeriksaanList: tugasPemeriksaanList,
                [name]
                    : event.value
            }
        )
    }

    async renderEmployeeOption() {
        const response = await EmployeeService.getEmployeeList();

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
    // handleSubmit(event) {
    //     if(event.nama == "Simpan"){
    //         event.preventDefault();
    //         const rencanaPemeriksaan = {
    //             namaRencana: this.state.namaRencana,
    //             linkMajelis: this.state.linkMajelis,
    //             status: 2,
    //             tugasPemeriksaanList: this.state.tugasPemeriksaanList[{
    //                 kantorCabang: this.state.kantorCabang,
    //                 idQA: this.state.idQA,
    //                 tanggalMulai: this.state.tanggalMulai,
    //                 tanggalSelesai: this.state.tanggalSelesai,
    //             }]
    //         }
    //         RencanaPemeriksaanService.addRencanaPemeriksaan(rencanaPemeriksaan)
    //         .then(() => this.setRedirect());
    //     }
    //     else if (event.nama == "Draft"){
    //         event.preventDefault();
    //         const rencanaPemeriksaan = {
    //             namaRencana: this.state.namaRencana,
    //             linkMajelis: this.state.linkMajelis,
    //             status: this.state.status,
    //             tugasPemeriksaanList: this.state.tugasPemeriksaanList[{
    //                 kantorCabang: this.state.kantorCabang,
    //                 idQA: this.state.idQA,
    //                 tanggalMulai: this.state.tanggalMulai,
    //                 tanggalSelesai: this.state.tanggalSelesai,
    //             }]
    //         }
    //         RencanaPemeriksaanService.addRencanaPemeriksaan(rencanaPemeriksaan)
    //         .then(() => this.setRedirect());
    //     }
    
    // }

    handleSubmit(event) {
        event.preventDefault();
        const rencanaPemeriksaan = {
            namaRencana: this.state.namaRencana,
            linkMajelis: this.state.linkMajelis,
            status: this.state.status,
            tugasPemeriksaanList: this.state.tugasPemeriksaanList[{
                kantorCabang: this.state.kantorCabang,
                idQA: this.state.idQA,
                tanggalMulai: this.state.tanggalMulai,
                tanggalSelesai: this.state.tanggalSelesai,
            }]
        }
        RencanaPemeriksaanService.addRencanaPemeriksaan(rencanaPemeriksaan)
        .then(() => this.setRedirect());
    }

    outerInputDefinition() {
        // const forms = [];
        // for (let i = 1; i < this.state.tugasPemeriksaanList.length; i++) {
        //     forms.push(
        //         <SirioForm
        //             key={i}
        //             childForm
        //             id={i}
        //             inputDefinition={this.innerInputDefinition(i)}
        //             footerButton={this.childFooterButton(i)}
        //         />
        //     )
        // }
        return (
            
            [
                {
                    fullComponent:
                        <SirioForm
                            id={0}
                            noHeader
                            isInnerForm
                            inputDefinition={this.innerInputDefinition(0)}
                            
                        />
                    
                }
                //,{  
                    // fullComponent:
                    //     <div className="w-100 text-right">
                    //         <SirioButton blue recommended
                    //             classes="mr-3"
                    //             onClick={() => this.addForm()}
                    //         >
                    //             Tambah Tugas
                    //         </SirioButton>
                    //     </div>
                //}
                ,{  
                    label: "Nama Rencana*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "link",
                    value: this.state.namaRencana,
                    placeholder: "Masukan link majelis"
                }, {  
                    label: "Link Pemeriksaan*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "link",
                    value: this.state.linkMajelis,
                    placeholder: "Masukan link majelis"
                }  
                      
            ]
        )
    }
 
    getBetween(){
        const forms = [];
        for (let i = 1; i < this.state.tugasPemeriksaanList.length; i++) {
            forms.push(
                <SirioForm
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
                <div className="w-100 text-right">
                    <SirioButton blue recommended
                        classes="mr-3"
                        onClick={() => this.addForm()}
                    >
                        Tambah Tugas
                    </SirioButton>
                </div>
            </>
        )
       
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    innerInputDefinition(index) {
        return (
            [
                {
                    label: "Kantor Cabang*",
                    handleChange: this.handleMultipleSelectChange,
                    type: "select",
                    name: "kantorCabang",
                    value: this.state.kantorCabang,
                    optionList: this.state.kantorOptionList
                }, {
                    label: "QA Officer*",
                    handleChange: this.handleMultipleSelectChange,
                    type: "select",
                    name: "idQA",
                    value: this.state.idQA,
                    optionList: this.state.employeeOptionList
                },{
                    label: "Tanggal Mulai*",
                    handleChange: this.handleMultipleChange,
                    type: "date",
                    name: "tanggalMulai",
                    value: this.state.tanggalMulai,
                    placeholder: "Masukan tanggal mulai"
                },{
                    label: "Tanggal Selesai*",
                    handleChange: this.handleMultipleChange,
                    type: "date",
                    name: "tanggalSelesai",
                    value: this.state.tanggalSelesai,
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
                    onClick={(event)  => this.handleSubmit(event)}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    onClick={(event)  => this.handleSubmit(event)}>
                >
                    Draft
                </SirioButton>
                <SirioButton purple
                    onClick={() => window.location.href = "/manager/rencanaPemeriksaan"}>
                >
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
                    betweenTitleSubtitle={this.getBetween()}
                    inputDefinition={this.outerInputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                    
                    
                />
                
            </>
           
        );
    }
}