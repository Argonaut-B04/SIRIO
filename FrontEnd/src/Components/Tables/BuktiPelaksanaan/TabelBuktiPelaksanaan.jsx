import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioTable from '../SirioTable';
import RekomendasiService from '../../../Services/RekomendasiService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import classes from '../Rekomendasi/TabelRekomendasi.module.css';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';
import AuthenticationService from "../../../Services/AuthenticationService";
import SirioAxiosBase from '../../../Services/SirioAxiosBase';

class TabelBuktiPelaksanaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            role: AuthenticationService.getRole(),
            openNotification: true
        }

        this.renderRows = this.renderRows.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    endNotification() {
        this.setState({
            openNotification: false
        })
    }

    async renderRows() {
        const response = await RekomendasiService.getRekomendasiByLoggedInUser();

        this.setState({
            rowList: response.data.result
        })
    }

    dataFormatter() {
        const dataRekomendasi = []
        for (var i in this.state.rowList) {
            if (this.state.rowList[i].status === "Sedang Dilaksanakan" || this.state.rowList[i].status === "Selesai") {
                dataRekomendasi.push(this.state.rowList[i]);
            }
        }
        return dataRekomendasi;
    }

    statusFormatter(cell) {
        switch (cell) {
            case "Menunggu Persetujuan":
                return (
                    <span style={{ color: '#8E8E8E' }}>{cell}</span>
                );
            case "Disetujui":
                return (
                    <span style={{ color: '#5DBCD2' }}>{cell}</span>
                );
            case "Ditolak":
                return (
                    <span style={{ color: '#FF0000' }}>{cell}</span>
                );
            default:
                return (cell);
        }
    }

    getButtons(cell, row) {
        const status = row.statusBukti;
        const tanpaBukti = status === null;
        const adaBukti = status === "Menunggu Persetujuan" || status === "Disetujui" || status === "Ditolak";

        if (tanpaBukti) {
            return (
                <NavLink to={{
                    pathname: "/bukti-pelaksanaan/tambah",
                    state: {
                        id: row.id
                    }
                }}>
                    <SirioButton
                        purple
                        hover
                    >
                        Tambah Bukti
                    </SirioButton>
                </NavLink>
            )
        } else if (adaBukti) {
            return (
                <NavLink to={{
                    pathname: "/bukti-pelaksanaan/detail",
                    state: {
                        id: row.id
                    }
                }}>
                    <SirioButton
                        purple
                        hover
                    >
                        Detail Bukti
                    </SirioButton>
                </NavLink>
            )
        }
    }

    getColumns() {
        const role = this.state.role;
        const firstColumn = role === "admin" || role === "Manajer Operational Risk" ||
            role === "QA Officer Operational Risk" || role === "Super QA Officer Operational Risk";
        const secondColumn = role === "Branch Manager";

        if (firstColumn) {
            return (
                [{
                    dataField: 'keterangan',
                    text: 'KETERANGAN',
                    sort: true,
                    classes: classes.rowItem,
                    headerClasses: classes.colheader,
                    headerStyle: (colum, colIndex) => {
                        return { width: "20%", textAlign: 'left' };
                    }
                }, {
                    dataField: 'namaKantorCabang',
                    text: 'KANTOR CABANG',
                    sort: true,
                    classes: classes.rowItem,
                    headerClasses: classes.colheader,
                    headerStyle: (colum, colIndex) => {
                        return { width: "15%", textAlign: 'left' };
                    }
                }, {
                    dataField: 'tenggatWaktuDate',
                    text: 'TENGGAT WAKTU',
                    sort: true,
                    classes: classes.rowItem,
                    headerClasses: classes.colheader,
                    headerStyle: (colum, colIndex) => {
                        return { width: "15%", textAlign: 'left' };
                    },
                    formatter: SirioAxiosBase.formatDate
                }, {
                    dataField: 'durasi',
                    text: 'DURASI',
                    sort: true,
                    classes: classes.rowItem,
                    headerClasses: classes.colheader,
                    headerStyle: (colum, colIndex) => {
                        return { width: "10%", textAlign: 'left' };
                    }

                }, {
                    dataField: 'statusBukti',
                    text: 'STATUS BUKTI',
                    sort: true,
                    classes: classes.rowItem,
                    formatter: this.statusFormatter,
                    headerClasses: classes.colheader,
                    headerStyle: (colum, colIndex) => {
                        return { width: "20%", textAlign: 'left' };
                    }
                }, {
                    dataField: 'action',
                    text: '',
                    headerClasses: classes.colheader,
                    classes: classes.rowItem,
                    style: () => {
                        return { textAlign: 'center' }
                    },
                    formatter: this.getButtons
                }]
            );
        } else if (secondColumn) {
            return (
                [{
                    dataField: 'keterangan',
                    text: 'KETERANGAN',
                    sort: true,
                    classes: classes.rowItem,
                    headerClasses: classes.colheader,
                    headerStyle: (colum, colIndex) => {
                        return { width: "25%", textAlign: 'left' };
                    }

                }, {
                    dataField: 'tenggatWaktuDate',
                    text: 'TENGGAT WAKTU',
                    sort: true,
                    classes: classes.rowItem,
                    headerClasses: classes.colheader,
                    headerStyle: (colum, colIndex) => {
                        return { width: "20%", textAlign: 'left' };
                    },
                    formatter: (cell) => SirioAxiosBase.formatDate(cell)
                }, {
                    dataField: 'durasi',
                    text: 'DURASI',
                    sort: true,
                    classes: classes.rowItem,
                    headerClasses: classes.colheader,
                    headerStyle: (colum, colIndex) => {
                        return { width: "12%", textAlign: 'left' };
                    }

                }, {
                    dataField: 'statusBukti',
                    text: 'STATUS BUKTI',
                    sort: true,
                    classes: classes.rowItem,
                    formatter: this.statusFormatter,
                    headerClasses: classes.colheader,
                    headerStyle: (colum, colIndex) => {
                        return { width: "20%", textAlign: 'left' };
                    }
                }, {
                    dataField: 'action',
                    text: '',
                    headerClasses: classes.colheader,
                    classes: classes.rowItem,
                    style: () => {
                        return { textAlign: 'center' }
                    },
                    formatter: this.getButtons
                }]
            );
        }
    }

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    render() {
        return (
            <>
                <SirioTable
                    title="Daftar Bukti Pelaksanaan Rekomendasi"
                    data={this.dataFormatter()}
                    id='id'
                    columnsDefinition={this.getColumns()}
                    includeSearchBar
                />
                {this.props.location.state && this.props.location.state.addSuccess && this.state.openNotification &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Bukti pelaksanaan berhasil ditambahkan"
                        customConfirmText="Tutup"
                        onClick={this.endNotification}
                    />
                }
                {this.props.location.state && this.props.location.state.editSuccess && this.state.openNotification &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Bukti pelaksanaan berhasil diubah"
                        customConfirmText="Tutup"
                        onClick={this.endNotification}
                    />
                }
                {this.props.location.state && this.props.location.state.approveSuccess && this.state.openNotification &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Bukti pelaksanaan berhasil disetujui"
                        customConfirmText="Tutup"
                        onClick={this.endNotification}
                    />
                }
                {this.props.location.state && this.props.location.state.rejectSuccess && this.state.openNotification &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Bukti pelaksanaan berhasil ditolak"
                        customConfirmText="Tutup"
                        onClick={this.endNotification}
                    />
                }
            </>
        );
    }
}

export default withRouter(TabelBuktiPelaksanaan);